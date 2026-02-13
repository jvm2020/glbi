package ca.glbi.app.calc;

public enum ClawbackScenario {
    SCENARIO_1("Scenario 1: 50% Clawback", 0.50),
    SCENARIO_2("Scenario 2: 25% Clawback", 0.25),
    SCENARIO_3("Scenario 3: 15% Clawback", 0.15),
    SCENARIO_4("Scenario 4: Graduated Rates", 0.0); // Rate is variable

    private final String name;
    private final double flatRate;

    ClawbackScenario(String name, double flatRate) {
        this.name = name;
        this.flatRate = flatRate;
    }

    public String getName() {
        return name;
    }

    public double getFlatRate() {
        return flatRate;
    }
}
