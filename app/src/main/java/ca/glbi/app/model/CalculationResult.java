package ca.glbi.app.model;

public class CalculationResult {
    private final String scenarioName;
    private final int netBenefit;
    private final String description;

    public CalculationResult(String scenarioName, int netBenefit, String description) {
        this.scenarioName = scenarioName;
        this.netBenefit = netBenefit;
        this.description = description;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public int getNetBenefit() {
        return netBenefit;
    }

    public String getDescription() {
        return description;
    }
}
