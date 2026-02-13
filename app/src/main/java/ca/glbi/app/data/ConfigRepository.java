package ca.glbi.app.data;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import ca.glbi.app.model.GlbiConfig;

public class ConfigRepository {
    private static ConfigRepository instance;
    private GlbiConfig config;
    private final Context context;

    private ConfigRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized ConfigRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ConfigRepository(context);
        }
        return instance;
    }

    public GlbiConfig getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    private void loadConfig() {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config/glbi_config.json");
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            
            Gson gson = new Gson();
            config = gson.fromJson(reader, GlbiConfig.class);
            
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Return default config if loading fails
            config = createDefaultConfig();
        }
    }

    private GlbiConfig createDefaultConfig() {
        // Fallback default configuration
        return new GlbiConfig();
    }

    public int getBaseBenefitSingle() {
        return getConfig().getBenefits().getBaseBenefitSingle();
    }

    public int getBaseBenefitHousehold() {
        return getConfig().getBenefits().getBaseBenefitHousehold();
    }
}
