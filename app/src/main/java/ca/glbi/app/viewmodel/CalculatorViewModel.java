package ca.glbi.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ca.glbi.app.calc.ClawbackCalculator;
import ca.glbi.app.data.ConfigRepository;
import ca.glbi.app.model.CalculationResult;
import ca.glbi.app.model.GlbiConfig;

public class CalculatorViewModel extends AndroidViewModel {
    private final ConfigRepository configRepository;
    private final MutableLiveData<List<CalculationResult>> results = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    // User inputs
    private int age = 30;
    private int income = 0;
    private boolean isHousehold = false;

    public CalculatorViewModel(@NonNull Application application) {
        super(application);
        configRepository = ConfigRepository.getInstance(application);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public void setHousehold(boolean household) {
        isHousehold = household;
    }

    public LiveData<List<CalculationResult>> getResults() {
        return results;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void calculate() {
        // Validate inputs
        if (!ClawbackCalculator.isValidAge(age)) {
            error.setValue("Age must be between 15 and 120");
            return;
        }

        if (!ClawbackCalculator.isValidIncome(income)) {
            error.setValue("Income must be non-negative");
            return;
        }

        error.setValue(null);

        GlbiConfig config = configRepository.getConfig();
        int baseBenefit = isHousehold ? 
            config.getBenefits().getBaseBenefitHousehold() : 
            config.getBenefits().getBaseBenefitSingle();

        List<CalculationResult> calculationResults = new ArrayList<>();

        // Scenario 1: 50% clawback
        GlbiConfig.Scenario scenario1 = config.getClawbackScenarios().getScenario1();
        int result1 = ClawbackCalculator.calculateScenario1(baseBenefit, income, scenario1.getRate());
        calculationResults.add(new CalculationResult(scenario1.getName(), result1, scenario1.getDescription()));

        // Scenario 2: 25% clawback
        GlbiConfig.Scenario scenario2 = config.getClawbackScenarios().getScenario2();
        int result2 = ClawbackCalculator.calculateScenario2(baseBenefit, income, scenario2.getRate());
        calculationResults.add(new CalculationResult(scenario2.getName(), result2, scenario2.getDescription()));

        // Scenario 3: 15% clawback
        GlbiConfig.Scenario scenario3 = config.getClawbackScenarios().getScenario3();
        int result3 = ClawbackCalculator.calculateScenario3(baseBenefit, income, scenario3.getRate());
        calculationResults.add(new CalculationResult(scenario3.getName(), result3, scenario3.getDescription()));

        // Scenario 4: Graduated rates
        GlbiConfig.GraduatedScenario scenario4 = config.getClawbackScenarios().getScenario4();
        int result4 = ClawbackCalculator.calculateScenario4(baseBenefit, income, scenario4.getBrackets());
        calculationResults.add(new CalculationResult(scenario4.getName(), result4, scenario4.getDescription()));

        results.setValue(calculationResults);
    }

    public int getAge() {
        return age;
    }

    public int getIncome() {
        return income;
    }

    public boolean isHousehold() {
        return isHousehold;
    }
}
