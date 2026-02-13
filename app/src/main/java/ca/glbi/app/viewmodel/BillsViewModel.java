package ca.glbi.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ca.glbi.app.data.ConfigRepository;
import ca.glbi.app.model.GlbiConfig;

public class BillsViewModel extends AndroidViewModel {
    private final ConfigRepository configRepository;
    private final MutableLiveData<GlbiConfig> config = new MutableLiveData<>();

    public BillsViewModel(@NonNull Application application) {
        super(application);
        configRepository = ConfigRepository.getInstance(application);
        loadConfig();
    }

    private void loadConfig() {
        config.setValue(configRepository.getConfig());
    }

    public LiveData<GlbiConfig> getConfig() {
        return config;
    }
}
