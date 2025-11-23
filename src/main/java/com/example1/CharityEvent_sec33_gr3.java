package com.example1;

import java.time.LocalDateTime;
//Mustabir islam 1096561
//Tarek firas 1090479
//Fakhri Mohammed 1093231

public class CharityEvent_sec33_gr3 extends Event_sec33_gr3 {
    private String organizationName;
    private double targetAmount;
    private String beneficiaryType; // Children, Elderly, Healthcare, Education

    public CharityEvent_sec33_gr3(String title, LocalDateTime startTime, LocalDateTime endTime,
                        String location, String emirate, String description,
                        String organizationName, double targetAmount, String beneficiaryType) {
        super(title, startTime, endTime, location, "Charity", emirate, description);
        this.organizationName = organizationName;
        this.targetAmount = targetAmount;
        this.beneficiaryType = beneficiaryType;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    @Override
    public String getDetails() {
        return String.format(
                "║ Organization: %-44s ║\n" +
                        "║ Target: AED %-43.2f ║\n" +
                        "║ Beneficiary: %-45s ║\n",
                organizationName, targetAmount, beneficiaryType);
    }
}

