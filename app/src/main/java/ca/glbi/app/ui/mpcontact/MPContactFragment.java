package ca.glbi.app.ui.mpcontact;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import ca.glbi.app.R;
import ca.glbi.app.viewmodel.MPContactViewModel;

public class MPContactFragment extends Fragment {

    private MPContactViewModel viewModel;
    private TextInputEditText editSearch;
    private AutoCompleteTextView spinnerProvince;
    private RecyclerView recyclerMPs;
    private MPAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mp_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MPContactViewModel.class);

        editSearch = view.findViewById(R.id.edit_search);
        spinnerProvince = view.findViewById(R.id.spinner_province);
        recyclerMPs = view.findViewById(R.id.recycler_mps);

        setupRecyclerView();
        setupSearch();
        setupProvinceFilter();

        viewModel.getMPList().observe(getViewLifecycleOwner(), mps -> {
            if (mps != null) {
                adapter.setMPs(mps);
            }
        });

        viewModel.getProvinces().observe(getViewLifecycleOwner(), provinces -> {
            if (provinces != null) {
                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_dropdown_item_1line, provinces);
                spinnerProvince.setAdapter(provinceAdapter);
                if (!provinces.isEmpty()) {
                    spinnerProvince.setText(provinces.get(0), false);
                }
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new MPAdapter(mp -> {
            // Email MP button clicked
            String subject = getString(R.string.mp_contact_template_subject);
            String body = getString(R.string.mp_contact_template_body)
                    .replace("[MP Name]", mp.getName());
            
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mp.getEmail()});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            
            try {
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            } catch (android.content.ActivityNotFoundException ex) {
                // No email client installed
            }
        });
        
        recyclerMPs.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerMPs.setAdapter(adapter);
    }

    private void setupSearch() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchMPs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setupProvinceFilter() {
        spinnerProvince.setOnItemClickListener((parent, view, position, id) -> {
            String selectedProvince = (String) parent.getItemAtPosition(position);
            viewModel.filterByProvince(selectedProvince);
        });
    }
}
