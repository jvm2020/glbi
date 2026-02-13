package ca.glbi.app.model;

public class GlbiConfig {
    private String version;
    private String lastUpdated;
    private String buildDate;
    private String dataSource;
    private String dataSourceUrl;
    private String mpDataLastUpdated;
    private Benefits benefits;
    private ClawbackScenarios clawbackScenarios;
    private ParliamentaryLinks parliamentaryLinks;
    private Notes notes;

    public static class Benefits {
        private int baseBenefitSingle;
        private int baseBenefitHousehold;
        private String currency;

        public int getBaseBenefitSingle() {
            return baseBenefitSingle;
        }

        public int getBaseBenefitHousehold() {
            return baseBenefitHousehold;
        }

        public String getCurrency() {
            return currency;
        }
    }

    public static class ClawbackScenarios {
        private Scenario scenario1;
        private Scenario scenario2;
        private Scenario scenario3;
        private GraduatedScenario scenario4;

        public Scenario getScenario1() {
            return scenario1;
        }

        public Scenario getScenario2() {
            return scenario2;
        }

        public Scenario getScenario3() {
            return scenario3;
        }

        public GraduatedScenario getScenario4() {
            return scenario4;
        }
    }

    public static class Scenario {
        private String name;
        private double rate;
        private String description;

        public String getName() {
            return name;
        }

        public double getRate() {
            return rate;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class GraduatedScenario {
        private String name;
        private String description;
        private Bracket[] brackets;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Bracket[] getBrackets() {
            return brackets;
        }
    }

    public static class Bracket {
        private int minIncome;
        private int maxIncome;
        private double rate;

        public int getMinIncome() {
            return minIncome;
        }

        public int getMaxIncome() {
            return maxIncome;
        }

        public double getRate() {
            return rate;
        }
    }

    public static class ParliamentaryLinks {
        private java.util.Map<String, Bill> currentBills;
        private java.util.Map<String, Bill> previousBills;
        private Submission submission;

        public java.util.Map<String, Bill> getCurrentBills() {
            return currentBills;
        }

        public java.util.Map<String, Bill> getPreviousBills() {
            return previousBills;
        }

        public Submission getSubmission() {
            return submission;
        }
    }

    public static class Bill {
        private String title;
        private String session;
        private String url;
        private String description;

        public String getTitle() {
            return title;
        }

        public String getSession() {
            return session;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Submission {
        private String committeePage;
        private String clerkInstructions;
        private String senateCommittees;

        public String getCommitteePage() {
            return committeePage;
        }

        public String getClerkInstructions() {
            return clerkInstructions;
        }

        public String getSenateCommittees() {
            return senateCommittees;
        }
    }

    public static class Notes {
        private String disclaimer;
        private String calculations;
        private String ccbCppNote;

        public String getDisclaimer() {
            return disclaimer;
        }

        public String getCalculations() {
            return calculations;
        }

        public String getCcbCppNote() {
            return ccbCppNote;
        }
    }

    public String getVersion() {
        return version;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public String getMpDataLastUpdated() {
        return mpDataLastUpdated;
    }

    public Benefits getBenefits() {
        return benefits;
    }

    public ClawbackScenarios getClawbackScenarios() {
        return clawbackScenarios;
    }

    public ParliamentaryLinks getParliamentaryLinks() {
        return parliamentaryLinks;
    }

    public Notes getNotes() {
        return notes;
    }
}
