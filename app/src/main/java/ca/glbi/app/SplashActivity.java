package ca.glbi.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ca.glbi.app.data.ConfigRepository;
import ca.glbi.app.model.GlbiConfig;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2500; // 2.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Load and display data dates
        loadDataInfo();

        // Navigate to MainActivity after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }

    private void loadDataInfo() {
        ConfigRepository configRepository = ConfigRepository.getInstance(this);
        GlbiConfig config = configRepository.getConfig();

        TextView textBuildDate = findViewById(R.id.text_build_date);
        TextView textPboData = findViewById(R.id.text_pbo_data);
        TextView textMpData = findViewById(R.id.text_mp_data);

        if (config != null) {
            // Build date
            String buildDate = config.getBuildDate() != null ? config.getBuildDate() : "Unknown";
            textBuildDate.setText(getString(R.string.splash_build_date, buildDate));

            // PBO data date
            String pboDate = config.getLastUpdated() != null ? config.getLastUpdated() : "Unknown";
            textPboData.setText(getString(R.string.splash_pbo_data, pboDate));

            // MP data date
            String mpDate = config.getMpDataLastUpdated() != null ? config.getMpDataLastUpdated() : "Unknown";
            textMpData.setText(getString(R.string.splash_mp_data, mpDate));
        } else {
            textBuildDate.setText(getString(R.string.splash_build_date, "Unknown"));
            textPboData.setText(getString(R.string.splash_pbo_data, "Unknown"));
            textMpData.setText(getString(R.string.splash_mp_data, "Unknown"));
        }
    }
}
