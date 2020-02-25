package me.mrdaniel.npcs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.mrdaniel.npcs.NPCs;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.function.Supplier;

public class LatestVersionSupplier implements Supplier<Optional<String>> {

    @Override
    public Optional<String> get() {
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
            NPCs.getInstance().getLogger().error("Failed to get latest version: ", exc);
        }
        return Optional.empty();
    }
}
