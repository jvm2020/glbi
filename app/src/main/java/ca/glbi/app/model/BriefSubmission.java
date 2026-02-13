package ca.glbi.app.model;

public class BriefSubmission {
    private String name;
    private String email;
    private String province;
    private String riding;
    private String organization;
    private String positionStatement;
    private String fullBrief;

    public BriefSubmission() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRiding() {
        return riding;
    }

    public void setRiding(String riding) {
        this.riding = riding;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPositionStatement() {
        return positionStatement;
    }

    public void setPositionStatement(String positionStatement) {
        this.positionStatement = positionStatement;
    }

    public String getFullBrief() {
        return fullBrief;
    }

    public void setFullBrief(String fullBrief) {
        this.fullBrief = fullBrief;
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               province != null && !province.trim().isEmpty() &&
               positionStatement != null && !positionStatement.trim().isEmpty() &&
               fullBrief != null && !fullBrief.trim().isEmpty();
    }

    public boolean isEmailValid() {
        if (email == null || email.trim().isEmpty()) {
            return true; // Email is optional
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
