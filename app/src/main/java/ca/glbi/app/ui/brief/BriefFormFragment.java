package ca.glbi.app.ui.brief;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import ca.glbi.app.R;
import ca.glbi.app.model.BriefSubmission;
import ca.glbi.app.util.PdfExporter;
import ca.glbi.app.util.ShareUtil;
import ca.glbi.app.viewmodel.BriefViewModel;

public class BriefFormFragment extends Fragment {

    private BriefViewModel viewModel;
    private TextInputEditText editName, editEmail, editRiding, editOrganization, editPosition, editBrief;
    private AutoCompleteTextView editProvince;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brief_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BriefViewModel.class);

        editName = view.findViewById(R.id.edit_name);
        editEmail = view.findViewById(R.id.edit_email);
        editProvince = view.findViewById(R.id.edit_province);
        editRiding = view.findViewById(R.id.edit_riding);
        editOrganization = view.findViewById(R.id.edit_organization);
        editPosition = view.findViewById(R.id.edit_position);
        editBrief = view.findViewById(R.id.edit_brief);

        Button btnPreview = view.findViewById(R.id.btn_preview);
        Button btnExportPdf = view.findViewById(R.id.btn_export_pdf);
        Button btnShare = view.findViewById(R.id.btn_share);

        setupProvinceDropdown();

        btnPreview.setOnClickListener(v -> previewBrief());
        btnExportPdf.setOnClickListener(v -> exportBrief());
        btnShare.setOnClickListener(v -> shareBrief());

        viewModel.getValidationError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupProvinceDropdown() {
        String[] provinces = getResources().getStringArray(R.array.provinces);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, provinces);
        editProvince.setAdapter(adapter);
    }

    private BriefSubmission getBriefFromForm() {
        BriefSubmission brief = new BriefSubmission();
        brief.setName(getText(editName));
        brief.setEmail(getText(editEmail));
        brief.setProvince(getText(editProvince));
        brief.setRiding(getText(editRiding));
        brief.setOrganization(getText(editOrganization));
        brief.setPositionStatement(getText(editPosition));
        brief.setFullBrief(getText(editBrief));
        return brief;
    }

    private String getText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }

    private String getText(AutoCompleteTextView editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }

    private void previewBrief() {
        BriefSubmission brief = getBriefFromForm();
        viewModel.updateBrief(brief);

        if (viewModel.validateBrief()) {
            // TODO: Show preview dialog or navigate to preview screen
            Toast.makeText(requireContext(), "Preview feature coming soon", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportBrief() {
        BriefSubmission brief = getBriefFromForm();
        viewModel.updateBrief(brief);

        if (viewModel.validateBrief()) {
            try {
                File pdfFile = PdfExporter.exportBrief(requireContext(), brief);
                Toast.makeText(requireContext(), getString(R.string.brief_export_success) + ": " + pdfFile.getName(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), R.string.brief_export_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void shareBrief() {
        BriefSubmission brief = getBriefFromForm();
        viewModel.updateBrief(brief);

        if (viewModel.validateBrief()) {
            try {
                File pdfFile = PdfExporter.exportBrief(requireContext(), brief);
                ShareUtil.shareBrief(requireContext(), pdfFile);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), R.string.brief_export_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
