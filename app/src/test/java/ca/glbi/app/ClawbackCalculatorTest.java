package ca.glbi.app;

import org.junit.Test;

import ca.glbi.app.calc.ClawbackCalculator;
import ca.glbi.app.model.GlbiConfig;

import static org.junit.Assert.*;

/**
 * Unit tests for ClawbackCalculator
 */
public class ClawbackCalculatorTest {

    // Base benefit amounts from 2025 PBO report
    private static final int BASE_BENEFIT_SINGLE = 21903;
    private static final int BASE_BENEFIT_HOUSEHOLD = 30975;

    @Test
    public void testScenario1_50PercentClawback_NoIncome() {
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_SINGLE, 0, 0.50);
        assertEquals(BASE_BENEFIT_SINGLE, result);
    }

    @Test
    public void testScenario1_50PercentClawback_LowIncome() {
        int income = 10000;
        int expectedClawback = (int) (income * 0.50); // 5000
        int expectedBenefit = BASE_BENEFIT_SINGLE - expectedClawback; // 16903
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_SINGLE, income, 0.50);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testScenario1_50PercentClawback_HighIncome() {
        int income = 100000;
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_SINGLE, income, 0.50);
        assertEquals(0, result); // Benefit fully clawed back
    }

    @Test
    public void testScenario2_25PercentClawback() {
        int income = 20000;
        int expectedClawback = (int) (income * 0.25); // 5000
        int expectedBenefit = BASE_BENEFIT_SINGLE - expectedClawback; // 16903
        int result = ClawbackCalculator.calculateScenario2(BASE_BENEFIT_SINGLE, income, 0.25);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testScenario3_15PercentClawback() {
        int income = 30000;
        int expectedClawback = (int) (income * 0.15); // 4500
        int expectedBenefit = BASE_BENEFIT_SINGLE - expectedClawback; // 17403
        int result = ClawbackCalculator.calculateScenario3(BASE_BENEFIT_SINGLE, income, 0.15);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testScenario4_GraduatedRates_IncomeBelowFirstBracket() {
        GlbiConfig.Bracket[] brackets = createTestBrackets();
        int income = 20000; // In first bracket (0-30k at 50%)
        int expectedClawback = (int) (20000 * 0.50); // 10000
        int expectedBenefit = BASE_BENEFIT_SINGLE - expectedClawback; // 11903
        int result = ClawbackCalculator.calculateScenario4(BASE_BENEFIT_SINGLE, income, brackets);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testScenario4_GraduatedRates_IncomeInSecondBracket() {
        GlbiConfig.Bracket[] brackets = createTestBrackets();
        int income = 40000; // Spans first and second brackets
        // First bracket: 30000 * 0.50 = 15000
        // Second bracket: 10000 * 0.25 = 2500
        // Total clawback: 17500
        int expectedBenefit = BASE_BENEFIT_SINGLE - 17500; // 4403
        int result = ClawbackCalculator.calculateScenario4(BASE_BENEFIT_SINGLE, income, brackets);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testScenario4_GraduatedRates_IncomeInThirdBracket() {
        GlbiConfig.Bracket[] brackets = createTestBrackets();
        int income = 60000; // Spans all three brackets
        // First bracket: 30000 * 0.50 = 15000
        // Second bracket: 20000 * 0.25 = 5000
        // Third bracket: 10000 * 0.15 = 1500
        // Total clawback: 21500
        int expectedBenefit = BASE_BENEFIT_SINGLE - 21500; // 403
        int result = ClawbackCalculator.calculateScenario4(BASE_BENEFIT_SINGLE, income, brackets);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testScenario4_GraduatedRates_HighIncome() {
        GlbiConfig.Bracket[] brackets = createTestBrackets();
        int income = 200000;
        int result = ClawbackCalculator.calculateScenario4(BASE_BENEFIT_SINGLE, income, brackets);
        assertEquals(0, result); // Benefit fully clawed back
    }

    @Test
    public void testHouseholdBenefit_NoIncome() {
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_HOUSEHOLD, 0, 0.50);
        assertEquals(BASE_BENEFIT_HOUSEHOLD, result);
    }

    @Test
    public void testHouseholdBenefit_WithIncome() {
        int income = 20000;
        int expectedClawback = (int) (income * 0.50); // 10000
        int expectedBenefit = BASE_BENEFIT_HOUSEHOLD - expectedClawback; // 20975
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_HOUSEHOLD, income, 0.50);
        assertEquals(expectedBenefit, result);
    }

    @Test
    public void testValidAge_Valid() {
        assertTrue(ClawbackCalculator.isValidAge(15));
        assertTrue(ClawbackCalculator.isValidAge(30));
        assertTrue(ClawbackCalculator.isValidAge(65));
        assertTrue(ClawbackCalculator.isValidAge(120));
    }

    @Test
    public void testValidAge_Invalid() {
        assertFalse(ClawbackCalculator.isValidAge(14));
        assertFalse(ClawbackCalculator.isValidAge(121));
        assertFalse(ClawbackCalculator.isValidAge(0));
        assertFalse(ClawbackCalculator.isValidAge(-1));
    }

    @Test
    public void testValidIncome_Valid() {
        assertTrue(ClawbackCalculator.isValidIncome(0));
        assertTrue(ClawbackCalculator.isValidIncome(10000));
        assertTrue(ClawbackCalculator.isValidIncome(200000));
    }

    @Test
    public void testValidIncome_Invalid() {
        assertFalse(ClawbackCalculator.isValidIncome(-1));
        assertFalse(ClawbackCalculator.isValidIncome(-10000));
    }

    @Test
    public void testEdgeCase_ExactlyAtClawbackThreshold() {
        // When clawback equals base benefit
        int income = BASE_BENEFIT_SINGLE * 2; // At 50% rate, this zeros out benefit
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_SINGLE, income, 0.50);
        assertEquals(0, result);
    }

    @Test
    public void testEdgeCase_ZeroIncome() {
        int result = ClawbackCalculator.calculateScenario1(BASE_BENEFIT_SINGLE, 0, 0.50);
        assertEquals(BASE_BENEFIT_SINGLE, result);
    }

    private GlbiConfig.Bracket[] createTestBrackets() {
        GlbiConfig.Bracket bracket1 = new GlbiConfig.Bracket() {
            {
                java.lang.reflect.Field minIncomeField, maxIncomeField, rateField;
                try {
                    minIncomeField = GlbiConfig.Bracket.class.getDeclaredField("minIncome");
                    maxIncomeField = GlbiConfig.Bracket.class.getDeclaredField("maxIncome");
                    rateField = GlbiConfig.Bracket.class.getDeclaredField("rate");
                    minIncomeField.setAccessible(true);
                    maxIncomeField.setAccessible(true);
                    rateField.setAccessible(true);
                    minIncomeField.setInt(this, 0);
                    maxIncomeField.setInt(this, 30000);
                    rateField.setDouble(this, 0.50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        GlbiConfig.Bracket bracket2 = new GlbiConfig.Bracket() {
            {
                java.lang.reflect.Field minIncomeField, maxIncomeField, rateField;
                try {
                    minIncomeField = GlbiConfig.Bracket.class.getDeclaredField("minIncome");
                    maxIncomeField = GlbiConfig.Bracket.class.getDeclaredField("maxIncome");
                    rateField = GlbiConfig.Bracket.class.getDeclaredField("rate");
                    minIncomeField.setAccessible(true);
                    maxIncomeField.setAccessible(true);
                    rateField.setAccessible(true);
                    minIncomeField.setInt(this, 30000);
                    maxIncomeField.setInt(this, 50000);
                    rateField.setDouble(this, 0.25);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        GlbiConfig.Bracket bracket3 = new GlbiConfig.Bracket() {
            {
                java.lang.reflect.Field minIncomeField, maxIncomeField, rateField;
                try {
                    minIncomeField = GlbiConfig.Bracket.class.getDeclaredField("minIncome");
                    maxIncomeField = GlbiConfig.Bracket.class.getDeclaredField("maxIncome");
                    rateField = GlbiConfig.Bracket.class.getDeclaredField("rate");
                    minIncomeField.setAccessible(true);
                    maxIncomeField.setAccessible(true);
                    rateField.setAccessible(true);
                    minIncomeField.setInt(this, 50000);
                    maxIncomeField.setInt(this, 999999);
                    rateField.setDouble(this, 0.15);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return new GlbiConfig.Bracket[]{bracket1, bracket2, bracket3};
    }
}
