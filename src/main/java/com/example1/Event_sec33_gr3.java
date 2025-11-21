package com.example1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
public abstract class Event_sec33_gr3 implements Comparable<Event_sec33_gr3>{

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String category; // Cultural, Educational, Charity, Sport
    private String emirate;
    private String description;
    private int eventId;

    public static int idCounter = 1000;

    public Event_sec33_gr3(String title, LocalDateTime startTime, LocalDateTime endTime, String location, String category, String emirate, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.category = category;
        this.emirate = emirate;
        this.description = description;
        this.eventId = idCounter++;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmirate() {
        return emirate;
    }

    public void setEmirate(String emirate) {
        this.emirate = emirate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEventId() {
        return eventId;
    }
    public abstract String getDetails();

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.format(
                "\n╔════════════════════════════════════════════════════════════╗\n" +
                        "║ Event ID: %-48d ║\n" +
                        "║ Title: %-51s ║\n" +
                        "║ Category: %-48s ║\n" +
                        "║ Start: %-48s ║\n" +
                        "║ End: %-50s ║\n" +
                        "║ Location: %-48s ║\n" +
                        "║ Emirate: %-49s ║\n" +
                        "║ Description: %-45s ║\n" +
                        "%s" + // <--- THIS %s IS FOR SUBCLASS DETAILS
                        "╚════════════════════════════════════════════════════════════╝",
                eventId, title, category, startTime.format(formatter),
                endTime.format(formatter), location, emirate, description,
                getDetails() // <--- Calls the subclass method here
        );
    }

    @Override
    public int compareTo(Event_sec33_gr3 other) {
        return this.getStartTime().compareTo(other.getStartTime());
    }
}
