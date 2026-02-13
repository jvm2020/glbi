package ca.glbi.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ca.glbi.app.data.MPRepository;
import ca.glbi.app.model.MemberOfParliament;

public class MPContactViewModel extends AndroidViewModel {
    private final MPRepository mpRepository;
    private final MutableLiveData<List<MemberOfParliament>> mpList = new MutableLiveData<>();
    private final MutableLiveData<List<String>> provinces = new MutableLiveData<>();
    private String currentSearchQuery = "";
    private String currentProvince = "All Provinces";

    public MPContactViewModel(@NonNull Application application) {
        super(application);
        mpRepository = MPRepository.getInstance(application);
        loadData();
    }

    private void loadData() {
        mpList.setValue(mpRepository.getAllMPs());
        provinces.setValue(mpRepository.getProvincesList());
    }

    public LiveData<List<MemberOfParliament>> getMPList() {
        return mpList;
    }

    public LiveData<List<String>> getProvinces() {
        return provinces;
    }

    public void searchMPs(String query) {
        currentSearchQuery = query;
        filterMPs();
    }

    public void filterByProvince(String province) {
        currentProvince = province;
        filterMPs();
    }

    private void filterMPs() {
        List<MemberOfParliament> filtered = mpRepository.getMPsByProvince(currentProvince);
        
        if (currentSearchQuery != null && !currentSearchQuery.trim().isEmpty()) {
            List<MemberOfParliament> searchResults = mpRepository.searchMPs(currentSearchQuery);
            filtered.retainAll(searchResults);
        }
        
        mpList.setValue(filtered);
    }

    public void resetFilters() {
        currentSearchQuery = "";
        currentProvince = "All Provinces";
        loadData();
    }
}
