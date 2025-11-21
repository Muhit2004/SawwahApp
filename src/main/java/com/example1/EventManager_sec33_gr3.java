package com.example1;

import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
/**
 *Controller class that manages all events using TreeMap
 * Handles collision strategy using ArrayList for events with identical start times (Option 2)
 */

public class EventManager_sec33_gr3 {

    private TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> eventSchedule;

    public EventManager_sec33_gr3() {
        eventSchedule = new TreeMap<>();
    }

    public void addEvent(Event_sec33_gr3 event) throws InvalidEventException_sec33_gr3{

        validateEvent(event);

        LocalDateTime startTime = event.getStartTime();

        if(eventSchedule.containsKey(startTime)){
            eventSchedule.get(startTime).add(event);
            System.out.println("✓ Event added successfully (same time slot)!");
        }else{
            ArrayList<Event_sec33_gr3> eventList = new ArrayList<>();
            eventList.add(event);
            eventSchedule.put(startTime, eventList);
            System.out.println("✓ Event added successfully!");
        }

    }

    public boolean removeEvent(String title) {
        // 1. Create an iterator to safely loop through the map
        Iterator<Map.Entry<LocalDateTime, ArrayList<Event_sec33_gr3>>> iterator = eventSchedule.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<LocalDateTime, ArrayList<Event_sec33_gr3>> entry = iterator.next();
            ArrayList<Event_sec33_gr3> events = entry.getValue();

            // 2. Search inside the list for the title
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getTitle().equalsIgnoreCase(title)) {

                    // Remove the event from the list
                    events.remove(i);
                    System.out.println("✓ Event '" + title + "' removed successfully!");

                    // 3. KEY LOGIC: If the list is now empty, remove the whole Tree Node
                    if (events.isEmpty()) {
                        iterator.remove(); // Safe removal using Iterator
                        // NOTE: This automatically triggers Red-Black rebalancing!
                    }
                    return true;
                }
            }
        }
        System.out.println("Event not found.");
        return false;
    }
    public void removeOutdatedEvents() {

        LocalDateTime now = LocalDateTime.now();

        Iterator<Map.Entry<LocalDateTime, ArrayList<Event_sec33_gr3>>> iterator = eventSchedule.entrySet().iterator();

        int deletedCount = 0;

        while (iterator.hasNext()) {
            Map.Entry<LocalDateTime, ArrayList<Event_sec33_gr3>> entry = iterator.next();
            LocalDateTime eventTime = entry.getKey();

            // THE CHECK: Is this time BEFORE now?
            if (eventTime.isBefore(now)) {
                // YES: It is in the past.
                // We don't even need to look inside the ArrayList.
                // If the time is past, ALL events at that time are past.
                iterator.remove();
                deletedCount++;

            } else {
                // We found a time that is EQUAL to or AFTER now.
                // Because the TreeMap is SORTED, we know every node after this
                // is also in the future. We don't need to check them.
                break;
            }
        }
        System.out.println("✓ Maintenance: Removed " + deletedCount + " outdated time slots.");
    }

    public List<Event_sec33_gr3> searchEventsByDate(LocalDateTime date) {
        List<Event_sec33_gr3> results = new ArrayList<>();

        for (Map.Entry<LocalDateTime, ArrayList<Event_sec33_gr3>> entry : eventSchedule.entrySet()) {
            if (entry.getKey().toLocalDate().equals(date.toLocalDate())) {
                results.addAll(entry.getValue());
            }
        }

        return results;
    }
    public List<Event_sec33_gr3> searchEventsByCategory(String category) {
        List<Event_sec33_gr3> results = new ArrayList<>();

        for (ArrayList<Event_sec33_gr3> events : eventSchedule.values()) {
            for (Event_sec33_gr3 event : events) {
                if (event.getCategory().equalsIgnoreCase(category)) {
                    results.add(event);
                }
            }
        }

        return results;
    }
    public List<Event_sec33_gr3> searchEventsByEmirate(String location) {
        List<Event_sec33_gr3> results = new ArrayList<>();

        for(ArrayList<Event_sec33_gr3> events : eventSchedule.values()){
            for(Event_sec33_gr3 event : events){
                if(event.getEmirate().equalsIgnoreCase(location)){
                    results.add(event);
                }
            }
        }
        return results;
    }
    public List<Event_sec33_gr3> searchEventsByLocation(String location) {
        List<Event_sec33_gr3> results = new ArrayList<>();

        for (ArrayList<Event_sec33_gr3> events : eventSchedule.values()) {
            for (Event_sec33_gr3 event : events) {
                if (event.getLocation().toLowerCase().contains(location.toLowerCase())) {
                    results.add(event);
                }
            }
        }

        return results;
    }

    public boolean updateEvent(String title, Event_sec33_gr3 updatedEvent) throws InvalidEventException_sec33_gr3 {
        // Remove old event
        if (removeEvent(title)) {
            // Add updated event
            addEvent(updatedEvent);
            System.out.println("✓ Event updated successfully!");
            return true;
        }
        return false;
    }

    /**
     * Basic Operation 5: Display all events in chronological order
     * Uses in-order traversal (TreeMap automatically maintains sorted order)
     */
    public void displayAllEvents() {
        if (eventSchedule.isEmpty()) {
            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("No events scheduled.");
            System.out.println("═══════════════════════════════════════════════════════════\n");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           ALL EVENTS IN CHRONOLOGICAL ORDER               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        int count = 1;
        for (Map.Entry<LocalDateTime, ArrayList<Event_sec33_gr3>> entry : eventSchedule.entrySet()) {
            for (Event_sec33_gr3 event : entry.getValue()) {
                System.out.println("\n[" + count++ + "]" + event.toString());
            }
        }
    }

    public void displayTreeStructure() {
        System.out.println("\n🌳 RED-BLACK TREE STRUCTURE 🌳");
        System.out.println("═══════════════════════════════════════\n");

        if (eventSchedule.isEmpty()) {
            System.out.println("Tree is empty");
            return;
        }

        List<LocalDateTime> keys = new ArrayList<>(eventSchedule.keySet());
        int totalNodes = keys.size();
        int treeHeight = (int) Math.ceil(Math.log(totalNodes + 1) / Math.log(2));

        System.out.println("📊 Tree Statistics:");
        System.out.println("   Total Nodes: " + totalNodes);
        System.out.println("   Tree Height (approx): " + treeHeight);
        System.out.println("   Total Events: " + getTotalEventCount());
        System.out.println();

        printTree(keys, 0, keys.size() - 1, "", true);
    }

    private void printTree(List<LocalDateTime> keys, int start, int end,
                           String prefix, boolean isRight) {
        if (start > end) return;

        int mid = start + (end - start) / 2;
        LocalDateTime key = keys.get(mid);
        List<Event_sec33_gr3> events = eventSchedule.get(key);

        // Current node
        System.out.print(prefix);
        System.out.print(isRight ? "└── " : "├── ");
        System.out.print(key.format(DateTimeFormatter.ofPattern("MM-dd HH:mm")));

        // Show first event name if exists
        if (!events.isEmpty()) {
            System.out.print(" → " + events.get(0).getTitle());
            if (events.size() > 1) {
                System.out.print(" (+" + (events.size() - 1) + " more)");
            }
        }
        System.out.println();

        // Children
        String childPrefix = prefix + (isRight ? "    " : "│   ");

        // Left child (earlier dates)
        if (start < mid) {
            printTree(keys, start, mid - 1, childPrefix, false);
        }

        // Right child (later dates)
        if (mid < end) {
            printTree(keys, mid + 1, end, childPrefix, true);
        }
    }

    public void validateEvent(Event_sec33_gr3 event) throws InvalidEventException_sec33_gr3{
        if(event ==null){
            throw new InvalidEventException_sec33_gr3("Event cannot be null");
        }

        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            throw new InvalidEventException_sec33_gr3("Event title cannot be empty");
        }

        if (event.getStartTime() == null) {
            throw new InvalidEventException_sec33_gr3("Event start time cannot be null");
        }

        if (event.getEndTime() == null) {
            throw new InvalidEventException_sec33_gr3("Event end time cannot be null");
        }

        if (event.getEndTime().isBefore(event.getStartTime())) {
            throw new InvalidEventException_sec33_gr3("Event end time cannot be before start time");
        }

        if (event.getLocation() == null || event.getLocation().trim().isEmpty()) {
            throw new InvalidEventException_sec33_gr3("Event location cannot be empty");
        }

        if (event.getEmirate() == null || event.getEmirate().trim().isEmpty()) {
            throw new InvalidEventException_sec33_gr3("Event emirate cannot be empty");
        }

    }



    /**
     * Get total number of events in the system
     */
    public int getTotalEventCount() {
        int count = 0;
        for (ArrayList<Event_sec33_gr3> events : eventSchedule.values()) {
            count += events.size();
        }
        return count;
    }

    /**
     * Get total number of time slots (TreeMap nodes)
     */
    public int getTotalTimeSlots() {
        return eventSchedule.size();
    }

}

