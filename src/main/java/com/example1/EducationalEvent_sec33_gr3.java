package com.example1;

import java.time.LocalDateTime;
//Mustabir islam 1096561
//Tarek firas 1090479
//Fakhri Mohammed 1093231
public class EducationalEvent_sec33_gr3 extends Event_sec33_gr3{

    private String educationalType; //workshops, lectures
    private String educationalLevel;

    public EducationalEvent_sec33_gr3(String title, LocalDateTime startTime, LocalDateTime endTime, String location, String educationalType, String educationalLevel, String emirate, String description)
    {
        super(title, startTime, endTime, location, "Educational", emirate, description);
        this.educationalType = educationalType;
        this.educationalLevel = educationalLevel;
    }
    public String getEducationalType() {
        return educationalType;
    }
    public void setEducationalType(String educationalType) {
        this.educationalType = educationalType;

    }
    public String getEducationalLevel() {
        return educationalLevel;
    }
    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    @Override
    public String getDetails() {
        return String.format(
                "║ Edu Type: %-48s ║\n" +
                        "║ Level: %-51s ║\n",
                educationalType, educationalLevel);
    }
}
