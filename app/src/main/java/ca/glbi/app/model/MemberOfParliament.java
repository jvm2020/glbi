package ca.glbi.app.model;

public class MemberOfParliament {
    private String name;
    private String riding;
    private String province;
    private String party;
    private String email;
    private String phone;
    private String constituency;

    public MemberOfParliament() {
    }

    public MemberOfParliament(String name, String riding, String province, String party, String email, String phone) {
        this.name = name;
        this.riding = riding;
        this.province = province;
        this.party = party;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRiding() {
        return riding;
    }

    public void setRiding(String riding) {
        this.riding = riding;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public boolean matchesSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return true;
        }
        String lowerQuery = query.toLowerCase().trim();
        return (name != null && name.toLowerCase().contains(lowerQuery)) ||
               (riding != null && riding.toLowerCase().contains(lowerQuery)) ||
               (province != null && province.toLowerCase().contains(lowerQuery)) ||
               (party != null && party.toLowerCase().contains(lowerQuery));
    }
}
