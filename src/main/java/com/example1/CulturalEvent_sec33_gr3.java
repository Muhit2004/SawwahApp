package com.example1;

import java.time.LocalDateTime;
//Mustabir islam 1096561
//Tarek firas 1090479
//Fakhri Mohammed 1093231
public class CulturalEvent_sec33_gr3 extends Event_sec33_gr3  {

    private String CulturalType; // Festival, Exhibition, Performance, Heritage

    private boolean RequiresRegistration;

    public CulturalEvent_sec33_gr3(String title, LocalDateTime startTime, LocalDateTime endTime,
                         String location, String emirate, String description,
                         String culturalType, boolean requiresRegistration) {
        super(title, startTime, endTime, location, "Cultural", emirate, description);
        this.CulturalType = culturalType;
        this.RequiresRegistration = requiresRegistration;
    }

    public String getCulturalType() {
        return CulturalType;
    }

    public void setCulturalType(String culturalType) {
        this.CulturalType = culturalType;
    }

    public boolean isRequiresRegistration() {
        return RequiresRegistration;
    }

    public void setRequiresRegistration(boolean requiresRegistration) {
        this.RequiresRegistration = requiresRegistration;
    }

    @Override
    public String getDetails() {
        return String.format(
                "║ Cultural Type: %-43s ║\n" +
                        "║ Registration: %-44s ║\n",
                CulturalType, (RequiresRegistration ? "Yes" : "No"));
    }
}
