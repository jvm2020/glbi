package ca.glbi.app.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;

import ca.glbi.app.R;
import ca.glbi.app.viewmodel.CalculatorViewModel;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel viewModel;
    private TextInputEditText editAge;
    private Slider sliderIncome;
    private TextView textIncomeValue;
    private MaterialButtonToggleGroup toggleHousehold;
    private TextView textResultsTitle;
    private RecyclerView recyclerResults;
    private CalculationResultAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);

        editAge = view.findViewById(R.id.edit_age);
        sliderIncome = view.findViewById(R.id.slider_income);
        textIncomeValue = view.findViewById(R.id.text_income_value);
        toggleHousehold = view.findViewById(R.id.toggle_household);
        Button btnCalculate = view.findViewById(R.id.btn_calculate);
        textResultsTitle = view.findViewById(R.id.text_results_title);
        recyclerResults = view.findViewById(R.id.recycler_results);

        setupRecyclerView();
        setupSlider();
        setupToggleGroup();

        btnCalculate.setOnClickListener(v -> calculate());

        viewModel.getResults().observe(getViewLifecycleOwner(), results -> {
            if (results != null && !results.isEmpty()) {
                textResultsTitle.setVisibility(View.VISIBLE);
                recyclerResults.setVisibility(View.VISIBLE);
                adapter.setResults(results);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new CalculationResultAdapter();
        recyclerResults.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerResults.setAdapter(adapter);
    }

    private void setupSlider() {
        sliderIncome.addOnChangeListener((slider, value, fromUser) -> {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
            currencyFormat.setMaximumFractionDigits(0);
            textIncomeValue.setText(currencyFormat.format(value));
        });
    }

    private void setupToggleGroup() {
        toggleHousehold.check(R.id.btn_single);
    }

    private void calculate() {
        String ageStr = editAge.getText() != null ? editAge.getText().toString() : "";
        if (ageStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter your age", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            int income = (int) sliderIncome.getValue();
            boolean isHousehold = toggleHousehold.getCheckedButtonId() == R.id.btn_household;

            viewModel.setAge(age);
            viewModel.setIncome(income);
            viewModel.setHousehold(isHousehold);
            viewModel.calculate();
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Invalid age value", Toast.LENGTH_SHORT).show();
        }
    }
}
