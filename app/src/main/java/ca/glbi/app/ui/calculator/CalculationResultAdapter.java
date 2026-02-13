package ca.glbi.app.ui.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ca.glbi.app.R;
import ca.glbi.app.model.CalculationResult;

public class CalculationResultAdapter extends RecyclerView.Adapter<CalculationResultAdapter.ViewHolder> {

    private List<CalculationResult> results = new ArrayList<>();

    public void setResults(List<CalculationResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calculation_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalculationResult result = results.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textScenarioName;
        private final TextView textDescription;
        private final TextView textNetBenefit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textScenarioName = itemView.findViewById(R.id.text_scenario_name);
            textDescription = itemView.findViewById(R.id.text_scenario_description);
            textNetBenefit = itemView.findViewById(R.id.text_net_benefit);
        }

        public void bind(CalculationResult result) {
            textScenarioName.setText(result.getScenarioName());
            textDescription.setText(result.getDescription());
            
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
            currencyFormat.setMaximumFractionDigits(0);
            textNetBenefit.setText(currencyFormat.format(result.getNetBenefit()));
        }
    }
}
