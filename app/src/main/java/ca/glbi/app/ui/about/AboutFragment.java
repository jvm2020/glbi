package ca.glbi.app.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.glbi.app.R;
import ca.glbi.app.viewmodel.AboutViewModel;

public class AboutFragment extends Fragment {

    private AboutViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AboutViewModel.class);

        TextView textVersion = view.findViewById(R.id.text_version);
        Button btnPrivacyPolicy = view.findViewById(R.id.btn_privacy_policy);

        viewModel.getVersionName().observe(getViewLifecycleOwner(), version -> {
            if (version != null) {
                textVersion.setText(version);
            }
        });

        btnPrivacyPolicy.setOnClickListener(v -> {
            // TODO: Open privacy policy or show in-app
        });
    }
}
