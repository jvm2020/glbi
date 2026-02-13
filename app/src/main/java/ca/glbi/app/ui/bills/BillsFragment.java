package ca.glbi.app.ui.bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ca.glbi.app.R;
import ca.glbi.app.model.GlbiConfig;
import ca.glbi.app.util.ShareUtil;
import ca.glbi.app.viewmodel.BillsViewModel;

public class BillsFragment extends Fragment {

    private BillsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bills, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BillsViewModel.class);

        Button btnViewS206 = view.findViewById(R.id.btn_view_s206);
        Button btnViewC223 = view.findViewById(R.id.btn_view_c223);
        Button btnViewS233 = view.findViewById(R.id.btn_view_s233);
        Button btnViewGuide = view.findViewById(R.id.btn_view_guide);
        Button btnSubmitBrief = view.findViewById(R.id.btn_submit_brief);

        viewModel.getConfig().observe(getViewLifecycleOwner(), config -> {
            if (config != null) {
                setupBillLinks(btnViewS206, btnViewC223, btnViewS233, btnViewGuide, config);
            }
        });

        btnSubmitBrief.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.briefFormFragment)
        );
    }

    private void setupBillLinks(Button btnS206, Button btnC223, Button btnS233, Button btnGuide, GlbiConfig config) {
        GlbiConfig.ParliamentaryLinks links = config.getParliamentaryLinks();

        btnS206.setOnClickListener(v -> {
            String url = links.getCurrentBills().get("S-206").getUrl();
            ShareUtil.openUrl(requireContext(), url);
        });

        btnC223.setOnClickListener(v -> {
            String url = links.getPreviousBills().get("C-223").getUrl();
            ShareUtil.openUrl(requireContext(), url);
        });

        btnS233.setOnClickListener(v -> {
            String url = links.getPreviousBills().get("S-233").getUrl();
            ShareUtil.openUrl(requireContext(), url);
        });

        btnGuide.setOnClickListener(v -> {
            String url = links.getSubmission().getClerkInstructions();
            ShareUtil.openUrl(requireContext(), url);
        });
    }
}
