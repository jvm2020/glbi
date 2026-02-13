package ca.glbi.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ca.glbi.app.BuildConfig;
import ca.glbi.app.data.ConfigRepository;
import ca.glbi.app.model.GlbiConfig;

public class AboutViewModel extends AndroidViewModel {
    private final ConfigRepository configRepository;
    private final MutableLiveData<String> versionName = new MutableLiveData<>();
    private final MutableLiveData<GlbiConfig> config = new MutableLiveData<>();

    public AboutViewModel(@NonNull Application application) {
        super(application);
        configRepository = ConfigRepository.getInstance(application);
        loadData();
    }

    private void loadData() {
        versionName.setValue(BuildConfig.VERSION_NAME);
        config.setValue(configRepository.getConfig());
    }

    public LiveData<String> getVersionName() {
        return versionName;
    }

    public LiveData<GlbiConfig> getConfig() {
        return config;
    }
}
