package com.example1;
//Mustabir islam 1096561
//Tarek firas 1090479
//Fakhri Mohammed 1093231
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.IOException;

//ARRAYLIST FOR COLLISION
//ADDED REGION BASED TREE AND CATAGORY BASED TREE

public class EventManager_sec33_gr3 {

    private TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> eventSchedule;

    //  Region-based TreeMaps  emirate-specific operations
    private Map<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> regionBasedTrees;

    //  Category-based TreeMaps  category-specific operations
    private Map<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> categoryBasedTrees;

    public EventManager_sec33_gr3() {
        eventSchedule = new TreeMap<>();

        // Initialize region-based trees for all UAE emirates
        regionBasedTrees = new HashMap<>();
        String[] emirates = {"Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al Quwain",
                            "Ras Al Khaimah", "Fujairah", "Al Ain"};
        for (String emirate : emirates) {
            regionBasedTrees.put(emirate, new TreeMap<>());
        }

        // Initialize category-based trees
        categoryBasedTrees = new HashMap<>();
        String[] categories = {"Cultural", "Educational", "Charity"};
        for (String category : categories) {
            categoryBasedTrees.put(category, new TreeMap<>());
        }
    }

    public void addEvent(Event_sec33_gr3 event) throws InvalidEventException_sec33_gr3{

        validateEvent(event);

        LocalDateTime startTime = event.getStartTime();

        // Add to main schedule
        if(eventSchedule.containsKey(startTime)){
            eventSchedule.get(startTime).add(event);
            System.out.println("✓ Event added successfully (same time slot)!");
        }else{
            ArrayList<Event_sec33_gr3> eventList = new ArrayList<>();
            eventList.add(event);
            eventSchedule.put(startTime, eventList);
            System.out.println("✓ Event added successfully!");
        }

        addToRegionTree(event);

        addToCategoryTree(event);
    }


    //  ADVANCED FEATURE: Add event to region-specific tree

    private void addToRegionTree(Event_sec33_gr3 event) throws InvalidEventException_sec33_gr3{
        validateEvent(event);
        String emirate = event.getEmirate();
        TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> regionTree = regionBasedTrees.get(emirate);

        if (regionTree == null) {
            regionTree = new TreeMap<>();
            regionBasedTrees.put(emirate, regionTree);
        }

        LocalDateTime startTime = event.getStartTime();
        if (regionTree.containsKey(startTime)) {
            regionTree.get(startTime).add(event);
        } else {
            ArrayList<Event_sec33_gr3> eventList = new ArrayList<>();
            eventList.add(event);
            regionTree.put(startTime, eventList);
        }
    }

    //ADVANCED FEATURE: Add event to category-specific tree

    private void addToCategoryTree(Event_sec33_gr3 event) throws InvalidEventException_sec33_gr3 {
        validateEvent(event);
        String category = event.getCategory();
        TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> categoryTree = categoryBasedTrees.get(category);

        if (categoryTree == null) {
            categoryTree = new TreeMap<>();
            categoryBasedTrees.put(category, categoryTree);
        }

        LocalDateTime startTime = event.getStartTime();
        if (categoryTree.containsKey(startTime)) {
            categoryTree.get(startTime).add(event);
        } else {
            ArrayList<Event_sec33_gr3> eventList = new ArrayList<>();
            eventList.add(event);
            categoryTree.put(startTime, eventList);
        }
    }


     // ADVANCED FEATURE: Load events from JSON file
     // Efficiently handles large datasets with automatic categorization

    public int loadEventsFromJSON(String filePath) {
        int loadedCount = 0;

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            System.out.println("\n📂 Loading events from JSON file: " + filePath);
            System.out.println("═══════════════════════════════════════════════════════════");

            for (int i = 0; i < jsonArray.size(); i++) {
                try {
                    JsonObject jsonEvent = jsonArray.get(i).getAsJsonObject();
                    Event_sec33_gr3 event = parseJsonEvent(jsonEvent);

                    if (event != null) {
                        addEvent(event);
                        loadedCount++;
                    }
                } catch (Exception e) {
                    System.err.println("⚠️ Warning: Skipped event " + (i + 1) + " - " + e.getMessage());
                }
            }

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("✓ Successfully loaded " + loadedCount + " events from JSON!");
            System.out.println("✓ Events distributed across " + regionBasedTrees.size() + " regional trees");
            System.out.println("✓ Events organized into " + categoryBasedTrees.size() + " category trees");

        } catch (IOException e) {
            System.err.println("❌ Error loading JSON file: " + e.getMessage());
        }

        return loadedCount;
    }


     // Parse JSON object to Event based on event type

    private Event_sec33_gr3 parseJsonEvent(JsonObject json) throws InvalidEventException_sec33_gr3 {
        String title = json.get("title").getAsString();
        LocalDateTime startTime = LocalDateTime.parse(json.get("startTime").getAsString());
        LocalDateTime endTime = LocalDateTime.parse(json.get("endTime").getAsString());
        String location = json.get("location").getAsString();
        String category = json.get("category").getAsString();
        String emirate = json.get("emirate").getAsString();
        String description = json.get("description").getAsString();
        String eventType = json.get("eventType").getAsString();

        switch (eventType) {
            case "Cultural":
                String festivalType = json.has("festivalType") ? json.get("festivalType").getAsString() : "Festival";
                boolean requiresRegistration = json.has("requiresRegistration") && json.get("requiresRegistration").getAsBoolean();
                return new CulturalEvent_sec33_gr3(title, startTime, endTime, location, emirate,
                                                   description, festivalType, requiresRegistration);

            case "Educational":
                String educationalType = json.has("EducationalType") ? json.get("EducationalType").getAsString() : "School" ;
                String educationalLevel = json.has("educationalLevel") ? json.get("educationalLevel").getAsString() : "Primary";
                return new EducationalEvent_sec33_gr3( title,  startTime,  endTime, location,  educationalType,
                         educationalLevel,  emirate, description);

            case "Charity":
                String organizingBody = json.has("organizingBody") ? json.get("organizingBody").getAsString() : "N/A";
                double targetAmount = json.has("targetAmount") ? json.get("targetAmount").getAsDouble() : 0.0;
                String cause = json.has("cause") ? json.get("cause").getAsString() : "Community Support";
                return new CharityEvent_sec33_gr3(title, startTime, endTime, location, emirate,
                                                  description, organizingBody, targetAmount, cause);

            default:
                throw new InvalidEventException_sec33_gr3("Unknown event type: " + eventType);
        }
    }


     // ADVANCED FEATURE: Get events for specific emirate using dedicated tree

    public List<Event_sec33_gr3> getEventsByRegion(String emirate) {
        List<Event_sec33_gr3> results = new ArrayList<>();
        TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> regionTree = regionBasedTrees.get(emirate);

        if (regionTree != null) {
            for (ArrayList<Event_sec33_gr3> events : regionTree.values()) {
                results.addAll(events);
            }
        }

        return results;
    }


     // ADVANCED FEATURE: Get events for specific category using dedicated tree

    public List<Event_sec33_gr3> getEventsByCategory(String category) {
        List<Event_sec33_gr3> results = new ArrayList<>();
        TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> categoryTree = categoryBasedTrees.get(category);

        if (categoryTree != null) {
            for (ArrayList<Event_sec33_gr3> events : categoryTree.values()) {
                results.addAll(events);
            }
        }

        return results;
    }


     // ADVANCED FEATURE: Display events grouped by region

    public void displayEventsByRegion() {
        displayGroupedEvents(regionBasedTrees, "EVENTS GROUPED BY EMIRATE (Regional Trees)", "🌍");
    }


     // ADVANCED FEATURE: Display events grouped by category

    public void displayEventsByCategory() {
        displayGroupedEvents(categoryBasedTrees, "EVENTS GROUPED BY CATEGORY (Category Trees)", "📋");
    }


     // ADVANCED FEATURE: Get statistics about regional distribution

    public void displayRegionalStatistics() {
        displayStatistics(regionBasedTrees, "REGIONAL EVENT DISTRIBUTION ANALYSIS", "Events per Emirate", true);
    }


     // ADVANCED FEATURE: Get statistics about category distribution

    public void displayCategoryStatistics() {
        displayStatistics(categoryBasedTrees, "CATEGORY EVENT DISTRIBUTION ANALYSIS", "Events per Category", false);
    }


     // Helper method to display events from any tree (region or category)

    private void displayGroupedEvents(Map<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> trees,
                                      String header, String icon) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-56s║%n", header);
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        for (Map.Entry<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> entry : trees.entrySet()) {
            TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> tree = entry.getValue();
            if (tree.isEmpty()) continue;

            int eventCount = tree.values().stream().mapToInt(List::size).sum();
            System.out.println("\n" + icon + " " + entry.getKey().toUpperCase() + " (" + eventCount + " events)");
            System.out.println("─────────────────────────────────────────────────────────");

            int count = 1;
            for (ArrayList<Event_sec33_gr3> eventList : tree.values()) {
                for (Event_sec33_gr3 event : eventList) {
                    System.out.println("  [" + count++ + "] " + event.getTitle() +
                        " - " + event.getStartTime().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")));
                }
            }
        }
    }

//
     // Helper method to display statistics for any tree (region or category)
//("Dubai", 5), ("Sharjah", 2)
    private void displayStatistics(Map<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> trees,
                                   String header, String label, boolean sortByCount) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-56s║%n", header);
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        System.out.println("📊 " + label + ":");
        System.out.println("─────────────────────────────────────────────────────────");

        List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        for (Map.Entry<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> entry : trees.entrySet()) {
            int count = entry.getValue().values().stream().mapToInt(List::size).sum();
            if (count > 0) {
                entries.add(new AbstractMap.SimpleEntry<>(entry.getKey(), count));
            }
        }

        if (sortByCount) {
            entries.sort((a, b) -> b.getValue().compareTo(a.getValue())); //efficiency O(nlogn)
        }

        for (Map.Entry<String, Integer> entry : entries) {
            String bar = "█".repeat(Math.min(entry.getValue(), 50));
            System.out.printf("  %-20s: %3d %s%n", entry.getKey(), entry.getValue(), bar);
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


     // Basic Operation 5: Display all events in chronological order
     // Uses in-order traversal (TreeMap automatically maintains sorted order)

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
