package me.mrdaniel.npcs.bstats;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

public class MetricsLite {

	public static final int B_STATS_VERSION = 1;
	private static final String URL = "https://bStats.org/submitData/sponge";
	private boolean enabled;
	private boolean logFailedRequests = false;

	private Logger logger;
	private final PluginContainer plugin;
	private String serverUUID;

	private static final List<Object> knownMetricsInstances = new ArrayList<>();
	private Path configDir;

	@Inject
	private MetricsLite(PluginContainer plugin, Logger logger, @ConfigDir(sharedRoot = true) Path configDir) {
		this.plugin = plugin;
		this.logger = logger;
		this.configDir = configDir;

		try { this.loadConfig(); }
		catch (IOException e) { logger.warn("Failed to load bStats config!", e); return; }

		if (!this.enabled) { return; }

		Class<?> usedMetricsClass = getFirstBStatsClass();
		if (usedMetricsClass == null) { return; }
		if (usedMetricsClass == getClass()) {
			linkMetrics(this);
			startSubmitting();
		}
		else {
			try { usedMetricsClass.getMethod("linkMetrics", Object.class).invoke(null, this); }
			catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				if (this.logFailedRequests) { logger.warn("Failed to link to first metrics class {}!", usedMetricsClass.getName(), e); }
			}
		}
	}

	public static void linkMetrics(Object metrics) {
		knownMetricsInstances.add(metrics);
	}

	public JsonObject getPluginData() {
		JsonObject data = new JsonObject();

		data.addProperty("pluginName", this.plugin.getName());
		data.addProperty("pluginVersion", this.plugin.getVersion().orElse("unknown"));
		data.add("customCharts", new JsonArray());

		return data;
	}

	private void startSubmitting() {
		final Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!Sponge.getPluginManager().isLoaded(plugin.getId())) { timer.cancel(); return; }
				Task.builder().execute(() -> submitData()).submit(plugin);
			}
		}, 1000*60*5, 1000*60*30);
	}

	private JsonObject getServerData() {
		JsonObject data = new JsonObject();

		data.addProperty("serverUUID", serverUUID);

		data.addProperty("playerAmount", Math.min(Sponge.getServer().getOnlinePlayers().size(), 200));
		data.addProperty("onlineMode", Sponge.getServer().getOnlineMode() ? 1 : 0);
		data.addProperty("minecraftVersion", Sponge.getGame().getPlatform().getMinecraftVersion().getName());

		data.addProperty("javaVersion", System.getProperty("java.version"));
		data.addProperty("osName", System.getProperty("os.name"));
		data.addProperty("osArch", System.getProperty("os.arch"));
		data.addProperty("osVersion", System.getProperty("os.version"));
		data.addProperty("coreCount", Runtime.getRuntime().availableProcessors());

		return data;
	}

	private void submitData() {
		final JsonObject data = getServerData();

		JsonArray pluginData = new JsonArray();
		for (Object metrics : knownMetricsInstances) {
			try {
				Object plugin = metrics.getClass().getMethod("getPluginData").invoke(metrics);
				if (plugin instanceof JsonObject) { pluginData.add((JsonObject) plugin); }
			}
			catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) { }
		}
		data.add("plugins", pluginData);

		new Thread(() ->  {
			try { sendData(data); }
			catch (Exception e) { if (this.logFailedRequests) { this.logger.warn("Could not submit plugin stats!", e); } }
		}).start();
	}

	private void loadConfig() throws IOException {
		Path configPath = this.configDir.resolve("bStats");
		if (!Files.exists(configPath)) { Files.createDirectory(configPath); }
		Path configFile = configPath.resolve("config.conf");
		HoconConfigurationLoader configurationLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
		CommentedConfigurationNode node;

		if (!Files.exists(configFile)) {
			Files.createFile(configFile);
			node = configurationLoader.load();

			node.getNode("enabled").setValue(true);
			node.getNode("serverUuid").setValue(UUID.randomUUID().toString());
			node.getNode("logFailedRequests").setValue(false);

			node.getNode("enabled").setComment(
					"bStats collects some data for plugin authors like how many servers are using their plugins.\n" +
							"To honor their work, you should not disable it.\n" +
							"This has nearly no effect on the server performance!\n" +
							"Check out https://bStats.org/ to learn more :)"
			);
			configurationLoader.save(node);
		}
		else { node = configurationLoader.load(); }

		this.enabled = node.getNode("enabled").getBoolean(true);
		this.serverUUID = node.getNode("serverUuid").getString();
		this.logFailedRequests = node.getNode("logFailedRequests").getBoolean(false);
	}

	private Class<?> getFirstBStatsClass() {
		Path configPath = this.configDir.resolve("bStats");
		configPath.toFile().mkdirs();
		File tempFile = new File(configPath.toFile(), "temp.txt");

		try {
			String className = readFile(tempFile);
			if (className != null) {
				try { return Class.forName(className); }
				catch (ClassNotFoundException ignored) { }
			}
			writeFile(tempFile, getClass().getName());
			return getClass();
		}
		catch (IOException e) {
			if (this.logFailedRequests) { this.logger.warn("Failed to get first bStats class!", e); }
			return null;
		}
	}

	private String readFile(File file) throws IOException {
		if (!file.exists()) { return null; }
		try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			return bufferedReader.readLine();
		}
	}

	private void writeFile(File file, String text) throws IOException {
		if (!file.exists()) { file.createNewFile(); }
		try (FileWriter fileWriter = new FileWriter(file); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			bufferedWriter.write(text);
			bufferedWriter.newLine();
			bufferedWriter.write("Note: This class only exists for internal purpose. You can ignore it :)");
		}
	}

	private static void sendData(JsonObject data) throws Exception {
		Validate.notNull(data, "Data cannot be null");
		HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();

		byte[] compressedData = compress(data.toString());

		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/json");
		connection.addRequestProperty("Connection", "close");
		connection.addRequestProperty("Content-Encoding", "gzip");
		connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("User-Agent", "MC-Server/" + B_STATS_VERSION);

		connection.setDoOutput(true);
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.write(compressedData);
		outputStream.flush();
		outputStream.close();

		connection.getInputStream().close();
	}

	private static byte[] compress(final String str) throws IOException {
		if (str == null) { return null; }
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
		gzip.write(str.getBytes("UTF-8"));
		gzip.close();
		return outputStream.toByteArray();
	}
}