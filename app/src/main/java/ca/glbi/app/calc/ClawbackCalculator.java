package ca.glbi.app.calc;

import ca.glbi.app.model.GlbiConfig;

public class ClawbackCalculator {

    /**
     * Calculate net GLBI benefit for Scenario 1: 50% flat clawback
     */
    public static int calculateScenario1(int baseBenefit, int income, double clawbackRate) {
        int clawback = (int) (income * clawbackRate);
        return Math.max(0, baseBenefit - clawback);
    }

    /**
     * Calculate net GLBI benefit for Scenario 2: 25% flat clawback
     */
    public static int calculateScenario2(int baseBenefit, int income, double clawbackRate) {
        int clawback = (int) (income * clawbackRate);
        return Math.max(0, baseBenefit - clawback);
    }

    /**
     * Calculate net GLBI benefit for Scenario 3: 15% flat clawback
     */
    public static int calculateScenario3(int baseBenefit, int income, double clawbackRate) {
        int clawback = (int) (income * clawbackRate);
        return Math.max(0, baseBenefit - clawback);
    }

    /**
     * Calculate net GLBI benefit for Scenario 4: Graduated rates
     * 0-30k: 50%, 30-50k: 25%, 50k+: 15%
     */
    public static int calculateScenario4(int baseBenefit, int income, GlbiConfig.Bracket[] brackets) {
        int totalClawback = 0;

        for (GlbiConfig.Bracket bracket : brackets) {
            if (income <= bracket.getMinIncome()) {
                continue;
            }

            int applicableIncome;
            if (income > bracket.getMaxIncome()) {
                applicableIncome = bracket.getMaxIncome() - bracket.getMinIncome();
            } else {
                applicableIncome = income - bracket.getMinIncome();
            }

            totalClawback += (int) (applicableIncome * bracket.getRate());

            if (income <= bracket.getMaxIncome()) {
                break;
            }
        }

        return Math.max(0, baseBenefit - totalClawback);
    }

    /**
     * Validate age is within acceptable range
     */
    public static boolean isValidAge(int age) {
        return age >= 15 && age <= 120;
    }

    /**
     * Validate income is non-negative
     */
    public static boolean isValidIncome(int income) {
        return income >= 0;
    }
}
