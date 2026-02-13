package ca.glbi.app.data;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.glbi.app.model.MemberOfParliament;

public class MPRepository {
    private static MPRepository instance;
    private List<MemberOfParliament> mpList;
    private final Context context;

    private MPRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized MPRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MPRepository(context);
        }
        return instance;
    }

    public List<MemberOfParliament> getAllMPs() {
        if (mpList == null) {
            loadMPs();
        }
        return mpList;
    }

    private void loadMPs() {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config/mp_contacts.json");
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            
            Gson gson = new Gson();
            Type listType = new TypeToken<List<MemberOfParliament>>(){}.getType();
            mpList = gson.fromJson(reader, listType);
            
            reader.close();
            inputStream.close();
            
            if (mpList == null) {
                mpList = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            mpList = new ArrayList<>();
        }
    }

    public List<MemberOfParliament> searchMPs(String query) {
        List<MemberOfParliament> allMPs = getAllMPs();
        if (query == null || query.trim().isEmpty()) {
            return allMPs;
        }

        List<MemberOfParliament> results = new ArrayList<>();
        for (MemberOfParliament mp : allMPs) {
            if (mp.matchesSearch(query)) {
                results.add(mp);
            }
        }
        return results;
    }

    public List<MemberOfParliament> getMPsByProvince(String province) {
        List<MemberOfParliament> allMPs = getAllMPs();
        if (province == null || province.trim().isEmpty() || province.equals("All Provinces")) {
            return allMPs;
        }

        List<MemberOfParliament> results = new ArrayList<>();
        for (MemberOfParliament mp : allMPs) {
            if (province.equalsIgnoreCase(mp.getProvince())) {
                results.add(mp);
            }
        }
        return results;
    }

    public List<String> getProvincesList() {
        List<String> provinces = new ArrayList<>();
        provinces.add("All Provinces");
        
        List<MemberOfParliament> allMPs = getAllMPs();
        for (MemberOfParliament mp : allMPs) {
            if (mp.getProvince() != null && !provinces.contains(mp.getProvince())) {
                provinces.add(mp.getProvince());
            }
        }
        
        Collections.sort(provinces.subList(1, provinces.size()));
        return provinces;
    }
}
