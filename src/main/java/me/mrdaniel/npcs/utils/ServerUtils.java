package me.mrdaniel.npcs.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ServerUtils {

	public static Cause getCause(final Object... causes) {
		Cause.Builder b = Cause.builder();
		for (Object cause : causes) { b.append(cause); }
		return b.build(EventContext.builder().build());
	}

	public static Cause getSpawnCause(@Nonnull final Entity e, final NamedCause... causes) {
		return getCause(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build(), causes);
	}

	public static Optional<String> getLatestVersion() {
		try {
			URL url = new URL("https://api.github.com/repos/Daniel12321/NPCs/releases/latest");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			InputStream is = connection.getInputStream();
			StringBuilder builder = new StringBuilder();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	        String line = reader.readLine();
	        while (line != null){
	            builder.append(line);
	            line = reader.readLine();
	        }

	        is.close();

			JsonElement element = new JsonParser().parse(builder.toString());
			String name = element.getAsJsonObject().get("name").getAsString();

			return Optional.of(name);
		}
		catch (final Exception exc) { exc.printStackTrace(); }
		return Optional.empty();
	}
}