package com.example1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
//Mustabir islam 1096561
//Tarek firas 1090479
//Fakhri Mohammed 1093231
public class SawwahApp_sec33_gr3 {
    private static EventManager_sec33_gr3 eventManager = new EventManager_sec33_gr3();

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     🌍 SAWWAH © - UAE COMMUNITY EVENT MANAGER           ║");
        System.out.println("║        Discover and Manage Community Events              ║");
        System.out.println("║         Supporting Community Year 2025 🇦🇪                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        // Load sample data
        System.out.println("Loading sample events...");
        loadSampleData();
        System.out.println("✓ Data loaded successfully!\n");

        // Start dynamic monitoring
        startDynamicMonitoring();
    }

    /**
     * Dynamic monitoring with console input (similar to UAEConnect)
     */
    public static void startDynamicMonitoring() {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║            DYNAMIC EVENT MONITORING SYSTEM               ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");

            System.out.println("\n📋 Available Commands:");
            System.out.println("─────────────────────────────────────────────────────────");
            System.out.println("  add          - Add a new event");
            System.out.println("  update       - Update an existing event");
            System.out.println("  remove       - Remove an event");
            System.out.println("  cleanup      - Remove all outdated events");
            System.out.println("  search       - Search for events");
            System.out.println("  display      - Display all events");
            System.out.println("  tree         - Display tree structure");
            System.out.println("");
            System.out.println(" ADVANCED FEATURES (Large Dataset Management):");
            System.out.println("  loadjson     - Load events from JSON file");
            System.out.println("  regional     - Display events grouped by emirate");
            System.out.println("  categories   - Display events grouped by category");
            System.out.println("  regstats     - Show regional distribution statistics");
            System.out.println("  catstats     - Show category distribution statistics");
            System.out.println("");
            System.out.println("  exit         - Exit monitoring");
            System.out.println("─────────────────────────────────────────────────────────");
            System.out.print("\n💬 Enter command: ");

            command = scanner.nextLine().trim().toLowerCase();

            if ("exit".equals(command)) {
                System.out.println("\n╔══════════════════════════════════════════════════════════╗");
                System.out.println("║     Thank you for using Sawwah © Event Manager!         ║");
                System.out.println("║        Promoting UAE's Community Year 2025 🇦🇪           ║");
                System.out.println("╚══════════════════════════════════════════════════════════╝");
                break;
            }

            try {
                switch (command) {
                    case "add":
                        handleAddEvent(scanner);
                        break;
                    case "update":
                        handleUpdateEvent(scanner);
                        break;
                    case "remove":
                        handleRemoveEvent(scanner);
                        break;
                    case "cleanup":
                        handleCleanupEvents();
                        break;
                    case "search":
                        handleSearchEvents(scanner);
                        break;
                    case "display":
                        eventManager.displayAllEvents();
                        break;
                    case "tree":
                        eventManager.displayTreeStructure();
                        break;
                    case "loadjson":
                        handleLoadJSON(scanner);
                        break;
                    case "regional":
                        eventManager.displayEventsByRegion();
                        break;
                    case "categories":
                        eventManager.displayEventsByCategory();
                        break;
                    case "regstats":
                        eventManager.displayRegionalStatistics();
                        break;
                    case "catstats":
                        eventManager.displayCategoryStatistics();
                        break;
                    default:
                        System.out.println(" Unknown command. Try again.");
                }
            } catch (Exception e) {
                System.err.println(" Error: " + e.getMessage());
            }
        }
        scanner.close();
    }


     //* Handle adding a new event

    private static void handleAddEvent(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                   ADD NEW EVENT                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        try {
            System.out.println("\nSelect Event Type:");
            System.out.println("1. Cultural Event");
            System.out.println("2. Educational Event");
            System.out.println("3. Charity Event");
            System.out.print("Enter choice (1-3): ");

            int eventType = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Event Title: ");
            String title = scanner.nextLine();

            System.out.print("Start Date & Time (yyyy-MM-dd HH:mm): ");
            String startStr = scanner.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(startStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.print("End Date & Time (yyyy-MM-dd HH:mm): ");
            String endStr = scanner.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.print("Location: ");
            String location = scanner.nextLine();

            System.out.print("Emirate: ");
            String emirate = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            Event_sec33_gr3 event = null;

            switch (eventType) {
                case 1: // Cultural Event
                    System.out.print("Cultural Type (Festival/Heritage/Performance/Exhibition): ");
                    String culturalType = scanner.nextLine();

                    System.out.print("Requires Registration? (true/false): ");
                    boolean requiresRegistration = Boolean.parseBoolean(scanner.nextLine().trim());

                    event = new CulturalEvent_sec33_gr3(title, startTime, endTime, location,
                            emirate, description, culturalType, requiresRegistration);
                    break;

                case 2: // Educational Event
                    System.out.print("Educational Type (Workshop/Lecture/Seminar): ");
                    String eduType = scanner.nextLine();

                    System.out.print("Education Level (High School/University/Professional): ");
                    String eduLevel = scanner.nextLine();

                    event = new EducationalEvent_sec33_gr3(title, startTime, endTime, location,
                            eduType, eduLevel, emirate, description);
                    break;

                case 3: // Charity Event
                    System.out.print("Organizing Body: ");
                    String organizingBody = scanner.nextLine();

                    System.out.print("Target Amount (AED): ");
                    double targetAmount = Double.parseDouble(scanner.nextLine().trim());

                    System.out.print("Beneficiary Category (Healthcare/Education/Families/Children/Elderly/Environment): ");
                    String beneficiary = scanner.nextLine();

                    event = new CharityEvent_sec33_gr3(title, startTime, endTime, location,
                            emirate, description, organizingBody, targetAmount, beneficiary);
                    break;

                default:
                    System.out.println("❌ Invalid event type!");
                    return;
            }

            if (event != null) {
                eventManager.addEvent(event);
                System.out.println("✅ Event added successfully!");
            }

        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format! Please use: yyyy-MM-dd HH:mm");
        } catch (InvalidEventException_sec33_gr3 e) {
            System.out.println("❌ Invalid Event: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }


    // * Handle updating an existing event

    private static void handleUpdateEvent(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                   UPDATE EVENT                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        try {
            System.out.print("Enter the title of the event to update: ");
            String oldTitle = scanner.nextLine();

            System.out.println("\nEnter NEW event details:");
            System.out.println("────────────────────────────────────────────────────────");

            System.out.println("\nSelect Event Type:");
            System.out.println("1. Cultural Event");
            System.out.println("2. Educational Event");
            System.out.println("3. Charity Event");
            System.out.print("Enter choice (1-3): ");

            int eventType = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("New Event Title: ");
            String title = scanner.nextLine();

            System.out.print("Start Date & Time (yyyy-MM-dd HH:mm): ");
            String startStr = scanner.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(startStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.print("End Date & Time (yyyy-MM-dd HH:mm): ");
            String endStr = scanner.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.print("Location: ");
            String location = scanner.nextLine();

            System.out.print("Emirate: ");
            String emirate = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            Event_sec33_gr3 updatedEvent = null;

            switch (eventType) {
                case 1: // Cultural Event
                    System.out.print("Cultural Type (Festival/Heritage/Performance/Exhibition): ");
                    String culturalType = scanner.nextLine();

                    System.out.print("Requires Registration? (true/false): ");
                    boolean requiresRegistration = Boolean.parseBoolean(scanner.nextLine().trim());

                    updatedEvent = new CulturalEvent_sec33_gr3(title, startTime, endTime, location,
                            emirate, description, culturalType, requiresRegistration);
                    break;

                case 2: // Educational Event
                    System.out.print("Educational Type (Workshop/Lecture/Seminar): ");
                    String eduType = scanner.nextLine();

                    System.out.print("Education Level (High School/University/Professional): ");
                    String eduLevel = scanner.nextLine();

                    updatedEvent = new EducationalEvent_sec33_gr3(title, startTime, endTime, location,
                            eduType, eduLevel, emirate, description);
                    break;

                case 3: // Charity Event
                    System.out.print("Organizing Body: ");
                    String organizingBody = scanner.nextLine();

                    System.out.print("Target Amount (AED): ");
                    double targetAmount = Double.parseDouble(scanner.nextLine().trim());

                    System.out.print("Beneficiary Category (Healthcare/Education/Families/Children/Elderly/Environment): ");
                    String beneficiary = scanner.nextLine();

                    updatedEvent = new CharityEvent_sec33_gr3(title, startTime, endTime, location,
                            emirate, description, organizingBody, targetAmount, beneficiary);
                    break;

                default:
                    System.out.println("❌ Invalid event type!");
                    return;
            }

            if (updatedEvent != null) {
                boolean success = eventManager.updateEvent(oldTitle, updatedEvent);
                if (success) {
                    System.out.println("✅ Event updated successfully!");
                } else {
                    System.out.println("❌ Event not found!");
                }
            }

        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format! Please use: yyyy-MM-dd HH:mm");
        } catch (InvalidEventException_sec33_gr3 e) {
            System.out.println("❌ Invalid Event: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }


   //  0 Handle removing an event

    private static void handleRemoveEvent(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                   REMOVE EVENT                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.print("Enter event title to remove: ");
        String title = scanner.nextLine();
        boolean removed = eventManager.removeEvent(title);
        if (removed) {
            System.out.println("✅ Event removed successfully!");
        } else {
            System.out.println("❌ Event not found!");
        }
    }


    //  Handle cleaning up outdated events

    private static void handleCleanupEvents() {
        System.out.println("\n🔄 Removing outdated events...");
        eventManager.removeOutdatedEvents();
        System.out.println("✅ Cleanup complete!");
    }


    //  Handle searching for events

    private static void handleSearchEvents(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                   SEARCH EVENTS                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.println("\nSearch By:");
        System.out.println("1. Date");
        System.out.println("2. Category");
        System.out.println("3. Emirate");
        System.out.println("4. Location");
        System.out.print("Enter choice (1-4): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            List<Event_sec33_gr3> results = null;

            switch (choice) {
                case 1:
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String dateStr = scanner.nextLine();
                    LocalDateTime searchDate = LocalDateTime.parse(dateStr + " 00:00",
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    results = eventManager.searchEventsByDate(searchDate);
                    break;

                case 2:
                    System.out.print("Enter category (Cultural/Educational/Charity): ");
                    String category = scanner.nextLine();
                    results = eventManager.searchEventsByCategory(category);
                    break;

                case 3:
                    System.out.print("Enter emirate: ");
                    String emirate = scanner.nextLine();
                    results = eventManager.searchEventsByEmirate(emirate);
                    break;

                case 4:
                    System.out.print("Enter location keyword: ");
                    String location = scanner.nextLine();
                    results = eventManager.searchEventsByLocation(location);
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
                    return;
            }

            if (results != null && !results.isEmpty()) {
                System.out.println("\n✅ Found " + results.size() + " event(s):");
                System.out.println("═══════════════════════════════════════════════════════════");
                for (Event_sec33_gr3 event : results) {
                    System.out.println(event);
                    System.out.println("───────────────────────────────────────────────────────────");
                }
            } else {
                System.out.println("\n❌ No events found matching your criteria.");
            }

        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format! Please use: yyyy-MM-dd");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    //  Load sample data for demonstration

    private static void loadSampleData()  {
        try {
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║       Loading Comprehensive Sample Event Data...        ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝\n");

            // ==================== CULTURAL EVENTS ====================

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "UAE National Day Celebration",
                    LocalDateTime.of(2025, 12, 2, 18, 0),
                    LocalDateTime.of(2025, 12, 2, 23, 0),
                    "Sheikh Zayed Grand Mosque",
                    "Abu Dhabi",
                    "Celebrate UAE's 54th National Day with fireworks and cultural performances",
                    "Festival",
                    true
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Sharjah Heritage Festival",
                    LocalDateTime.of(2025, 12, 15, 16, 0),
                    LocalDateTime.of(2025, 12, 15, 22, 0),
                    "Sharjah Heritage Area",
                    "Sharjah",
                    "Experience traditional Emirati culture, crafts, and cuisine",
                    "Heritage",
                    true
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Dubai Opera Gala Night",
                    LocalDateTime.of(2025, 11, 28, 19, 0),
                    LocalDateTime.of(2025, 11, 28, 22, 30),
                    "Dubai Opera House",
                    "Dubai",
                    "World-class opera performance featuring international artists",
                    "Performance",
                    true
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Abu Dhabi Art Exhibition",
                    LocalDateTime.of(2025, 12, 5, 10, 0),
                    LocalDateTime.of(2025, 12, 5, 20, 0),
                    "Louvre Abu Dhabi",
                    "Abu Dhabi",
                    "Contemporary Middle Eastern art showcase",
                    "Exhibition",
                    false
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Ajman Beach Festival",
                    LocalDateTime.of(2025, 12, 10, 16, 0),
                    LocalDateTime.of(2025, 12, 10, 23, 0),
                    "Ajman Corniche",
                    "Ajman",
                    "Family-friendly beach festival with food, music, and activities",
                    "Festival",
                    false
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "RAK Cultural Week",
                    LocalDateTime.of(2025, 12, 8, 17, 0),
                    LocalDateTime.of(2025, 12, 8, 21, 0),
                    "RAK National Museum",
                    "Ras Al Khaimah",
                    "Week-long celebration of RAK's history and traditions",
                    "Heritage",
                    true
            ));

            // ==================== EDUCATIONAL EVENTS ====================

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "AI & Machine Learning Workshop",
                    LocalDateTime.of(2025, 11, 25, 9, 0),
                    LocalDateTime.of(2025, 11, 25, 17, 0),
                    "Dubai World Trade Centre",
                    "Workshop",
                    "University",
                    "Dubai",
                    "Hands-on workshop on AI applications in smart cities"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Sustainability Lecture Series",
                    LocalDateTime.of(2025, 11, 27, 14, 0),
                    LocalDateTime.of(2025, 11, 27, 17, 0),
                    "Zayed University",
                    "Lecture",
                    "University",
                    "Abu Dhabi",
                    "Climate action and UAE's sustainable development goals"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Cybersecurity Summit",
                    LocalDateTime.of(2025, 12, 3, 10, 0),
                    LocalDateTime.of(2025, 12, 3, 18, 0),
                    "American University of Sharjah",
                    "Workshop",
                    "University",
                    "Sharjah",
                    "Protecting digital infrastructure in the modern age"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Youth Leadership Program",
                    LocalDateTime.of(2025, 12, 12, 9, 0),
                    LocalDateTime.of(2025, 12, 12, 15, 0),
                    "Fujairah Youth Center",
                    "Workshop",
                    "High School",
                    "Fujairah",
                    "Developing future leaders of the UAE"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Innovation in Healthcare Conference",
                    LocalDateTime.of(2025, 12, 18, 8, 30),
                    LocalDateTime.of(2025, 12, 18, 16, 0),
                    "Cleveland Clinic Abu Dhabi",
                    "Lecture",
                    "Professional",
                    "Abu Dhabi",
                    "Medical innovations and digital health technologies"
            ));

            // ==================== CHARITY EVENTS ====================

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Charity Run for Healthcare",
                    LocalDateTime.of(2025, 11, 30, 6, 0),
                    LocalDateTime.of(2025, 11, 30, 10, 0),
                    "Kite Beach",
                    "Dubai",
                    "5K charity run supporting children's healthcare initiatives",
                    "Dubai Cares",
                    100000.0,
                    "Healthcare"
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Food Drive for Families",
                    LocalDateTime.of(2025, 11, 23, 8, 0),
                    LocalDateTime.of(2025, 11, 23, 14, 0),
                    "Abu Dhabi Community Center",
                    "Abu Dhabi",
                    "Collecting and distributing food packages to families in need",
                    "Emirates Red Crescent",
                    75000.0,
                    "Families"
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Education Drive for Underprivileged",
                    LocalDateTime.of(2025, 12, 20, 14, 0),
                    LocalDateTime.of(2025, 12, 20, 18, 0),
                    "Fujairah Community Center",
                    "Fujairah",
                    "School supplies donation drive and fundraiser",
                    "Emirates Red Crescent",
                    50000.0,
                    "Education"
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Senior Care Initiative",
                    LocalDateTime.of(2025, 12, 7, 10, 0),
                    LocalDateTime.of(2025, 12, 7, 16, 0),
                    "Sharjah Care Home",
                    "Sharjah",
                    "Health checkups and social activities for elderly citizens",
                    "Sharjah Charity Association",
                    40000.0,
                    "Elderly"
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Orphan Support Fundraiser",
                    LocalDateTime.of(2025, 12, 14, 18, 0),
                    LocalDateTime.of(2025, 12, 14, 22, 0),
                    "Dubai Marina Mall",
                    "Dubai",
                    "Charity auction and dinner to support orphanages",
                    "Mohammed Bin Rashid Al Maktoum Charity",
                    150000.0,
                    "Children"
            ));

            // ==================== COLLISION EVENTS (Same Time Slots) ====================

            // Two events at the same time - 2025-11-28 19:00
            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Blockchain Technology Seminar",
                    LocalDateTime.of(2025, 11, 28, 19, 0), // Same as Dubai Opera
                    LocalDateTime.of(2025, 11, 28, 21, 0),
                    "Dubai Internet City",
                    "Lecture",
                    "Professional",
                    "Dubai",
                    "Understanding blockchain and cryptocurrency"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Science Fair for Schools",
                    LocalDateTime.of(2025, 12, 10, 16, 0), // Same as Ajman Beach Festival
                    LocalDateTime.of(2025, 12, 10, 20, 0),
                    "Dubai Science Park",
                    "Workshop",
                    "High School",
                    "Dubai",
                    "Student science projects and experiments showcase"
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Winter Clothing Drive",
                    LocalDateTime.of(2025, 12, 10, 16, 0), // Same time slot as above two
                    LocalDateTime.of(2025, 12, 10, 19, 0),
                    "Umm Al Quwain Central Mosque",
                    "Umm Al Quwain",
                    "Collecting winter clothes for those in need",
                    "UAE Red Crescent",
                    30000.0,
                    "Families"
            ));



            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Photography Exhibition",
                    LocalDateTime.of(2025, 11, 29, 11, 0),
                    LocalDateTime.of(2025, 11, 29, 19, 0),
                    "Etihad Modern Art Gallery",
                    "Abu Dhabi",
                    "UAE landscape photography by local artists",
                    "Exhibition",
                    false
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Space Exploration Talk",
                    LocalDateTime.of(2025, 12, 1, 16, 0),
                    LocalDateTime.of(2025, 12, 1, 18, 30),
                    "Mohammed Bin Rashid Space Centre",
                    "Lecture",
                    "University",
                    "Dubai",
                    "UAE's journey to space and future missions"
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Traditional Music Concert",
                    LocalDateTime.of(2025, 12, 6, 20, 0),
                    LocalDateTime.of(2025, 12, 6, 23, 0),
                    "Al Jahili Fort",
                    "Al Ain",
                    "Traditional Emirati music and dance performances",
                    "Performance",
                    true
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Blood Donation Campaign",
                    LocalDateTime.of(2025, 11, 26, 8, 0),
                    LocalDateTime.of(2025, 11, 26, 16, 0),
                    "Rashid Hospital",
                    "Dubai",
                    "Community blood donation drive - save lives",
                    "Dubai Health Authority",
                    0.0,
                    "Healthcare"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Entrepreneurship Bootcamp",
                    LocalDateTime.of(2025, 12, 9, 9, 0),
                    LocalDateTime.of(2025, 12, 9, 17, 0),
                    "in5 Innovation Centre",
                    "Workshop",
                    "Professional",
                    "Dubai",
                    "Start your business journey in the UAE"
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Book Fair 2025",
                    LocalDateTime.of(2025, 12, 11, 10, 0),
                    LocalDateTime.of(2025, 12, 11, 22, 0),
                    "Sharjah Expo Centre",
                    "Sharjah",
                    "International book fair with authors from around the world",
                    "Exhibition",
                    false
            ));

            eventManager.addEvent(new CharityEvent_sec33_gr3(
                    "Clean Beach Initiative",
                    LocalDateTime.of(2025, 11, 24, 7, 0),
                    LocalDateTime.of(2025, 11, 24, 11, 0),
                    "Jumeirah Beach",
                    "Dubai",
                    "Community beach cleanup and environmental awareness",
                    "Emirates Environmental Group",
                    20000.0,
                    "Environment"
            ));

            eventManager.addEvent(new EducationalEvent_sec33_gr3(
                    "Digital Marketing Masterclass",
                    LocalDateTime.of(2025, 12, 16, 13, 0),
                    LocalDateTime.of(2025, 12, 16, 18, 0),
                    "Dubai Knowledge Park",
                    "Workshop",
                    "Professional",
                    "Dubai",
                    "Modern marketing strategies for the digital age"
            ));

            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Film Festival Opening Night",
                    LocalDateTime.of(2025, 12, 13, 19, 30),
                    LocalDateTime.of(2025, 12, 13, 23, 0),
                    "VOX Cinemas",
                    "Dubai",
                    "Premiere of UAE and international films",
                    "Performance",
                    true
            ));

            // Past event for testing maintenance function
            eventManager.addEvent(new CulturalEvent_sec33_gr3(
                    "Past Event - Halloween Celebration",
                    LocalDateTime.of(2025, 10, 31, 18, 0),
                    LocalDateTime.of(2025, 10, 31, 22, 0),
                    "Global Village",
                    "Dubai",
                    "This event has already passed",
                    "Festival",
                    false
            ));

            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║              ✓ SAMPLE DATA LOADED SUCCESSFULLY!          ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("📊 Total Events: " + eventManager.getTotalEventCount());
            System.out.println("📊 Total Time Slots: " + eventManager.getTotalTimeSlots());
            System.out.println("📊 Events with Collisions: Multiple events at same time detected\n");

        } catch (InvalidEventException_sec33_gr3 e) {
            System.out.println("✗ Error loading sample data: " + e.getMessage());
        }
    }


   //  ADVANCED FEATURE: Handle loading events from JSON file

    private static void handleLoadJSON(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          LOAD EVENTS FROM JSON (Large Dataset)          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");



        try {

            String filePath;


                filePath = "events_large_dataset.json";


            System.out.println("\n🔄 Loading events from: " + filePath);
            int loadedCount = eventManager.loadEventsFromJSON(filePath);

            if (loadedCount > 0) {
                System.out.println("\n✅ Successfully loaded " + loadedCount + " events!");
                System.out.println("💡 Use 'regional' or 'categories' commands to view organized data");
                System.out.println("💡 Use 'regstats' or 'catstats' to see distribution analysis");
            } else {
                System.out.println("\n⚠️ No events were loaded. Check file path and format.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
