package me.mrdaniel.npcs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

public class ServerUtils {

	public static Cause getCause(final Object... causes) {
		Cause.Builder b = Cause.builder();
		for (Object cause : causes) {
			b.append(cause);
		}
		return b.build(EventContext.builder().build());
	}

//	public static Cause getSpawnCause(@Nonnull final Entity e, final NamedCause... causes) {
//		return getCause(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build(), causes);
//	}

	// TODO: Make async
	public static Optional<String> getLatestVersion() {
		try {
			URL url = new URL("https://api.github.com/repos/Daniel12321/NPCs/releases/latest");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	        BufferedReader reader = new BufferedReader(isr);
			StringBuilder builder = new StringBuilder();

	        String line = reader.readLine();
	        while (line != null){
	            builder.append(line);
	            line = reader.readLine();
	        }

	        is.close();
	        isr.close();
	        reader.close();

			JsonElement element = new JsonParser().parse(builder.toString());
			String name = element.getAsJsonObject().get("name").getAsString();

			return Optional.of(name);
		} catch (final Exception exc) {
			exc.printStackTrace();
		}
		return Optional.empty();
	}
}
