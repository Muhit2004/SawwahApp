package com.example1;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for EventManager_sec33_gr3
 * Tests all basic operations and edge cases as required by the assignment
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventManagerTest_sec33_gr3 {

    private EventManager_sec33_gr3 manager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        manager = new EventManager_sec33_gr3();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // ==================== BASIC OPERATION 1: ADD EVENT ====================

    @Test
    @Order(1)
    @DisplayName("Test 1.1: Add valid Cultural Event")
    void testAddValidCulturalEvent() {
        Assertions.assertDoesNotThrow(() -> {
            CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
                "Heritage Festival",
                LocalDateTime.of(2025, 12, 2, 18, 0),
                LocalDateTime.of(2025, 12, 2, 23, 0),
                "Dubai Creek",
                "Dubai",
                "Traditional cultural festival",
                "Festival",
                true
            );
            manager.addEvent(event);
            Assertions.assertTrue(outContent.toString().contains("✓ Event added successfully"));
        });
    }

    @Test
    @Order(2)
    @DisplayName("Test 1.2: Add valid Educational Event")
    void testAddValidEducationalEvent() {
        Assertions.assertDoesNotThrow(() -> {
            EducationalEvent_sec33_gr3 event = new EducationalEvent_sec33_gr3(
                "AI Workshop",
                LocalDateTime.of(2025, 11, 25, 14, 0),
                LocalDateTime.of(2025, 11, 25, 17, 0),
                "UAEU",
                "Workshop",
                "University",
                "Al Ain",
                "Introduction to AI and Machine Learning"
            );
            manager.addEvent(event);
            Assertions.assertTrue(outContent.toString().contains("✓ Event added successfully"));
        });
    }

    @Test
    @Order(3)
    @DisplayName("Test 1.3: Add valid Charity Event")
    void testAddValidCharityEvent() {
        Assertions.assertDoesNotThrow(() -> {
            CharityEvent_sec33_gr3 event = new CharityEvent_sec33_gr3(
                "Food Drive",
                LocalDateTime.of(2025, 11, 30, 9, 0),
                LocalDateTime.of(2025, 11, 30, 15, 0),
                "Community Center",
                "Sharjah",
                "Community food distribution",
                "Red Crescent",
                50000.0,
                "Families"
            );
            manager.addEvent(event);
            Assertions.assertTrue(outContent.toString().contains("✓ Event added successfully"));
        });
    }

    @Test
    @Order(4)
    @DisplayName("Test 1.4: Add multiple events with same start time (collision handling)")
    void testAddEventsWithSameStartTime() {
        Assertions.assertDoesNotThrow(() -> {
            LocalDateTime sameTime = LocalDateTime.of(2025, 12, 1, 10, 0);
            
            CulturalEvent_sec33_gr3 event1 = new CulturalEvent_sec33_gr3(
                "Art Exhibition",
                sameTime,
                LocalDateTime.of(2025, 12, 1, 18, 0),
                "Louvre Abu Dhabi",
                "Abu Dhabi",
                "Modern art exhibition",
                "Exhibition",
                false
            );
            
            EducationalEvent_sec33_gr3 event2 = new EducationalEvent_sec33_gr3(
                "Science Fair",
                sameTime,
                LocalDateTime.of(2025, 12, 1, 16, 0),
                "Dubai Science Park",
                "Lecture",
                "High School",
                "Dubai",
                "Student science projects showcase"
            );
            
            manager.addEvent(event1);
            outContent.reset();
            manager.addEvent(event2);
            
            Assertions.assertTrue(outContent.toString().contains("same time slot"),
                "Should handle collision with ArrayList");
        });
    }

    // ==================== EDGE CASE: INVALID EVENT DATA ====================

    @Test
    @Order(5)
    @DisplayName("Test 2.1: Add event with null title - should throw exception")
    void testAddEventWithNullTitle() {
        InvalidEventException_sec33_gr3 exception = Assertions.assertThrows(
            InvalidEventException_sec33_gr3.class,
            () -> {
                CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
                    null,
                    LocalDateTime.of(2025, 12, 5, 10, 0),
                    LocalDateTime.of(2025, 12, 5, 12, 0),
                    "Dubai Mall",
                    "Dubai",
                    "Test event",
                    "Festival",
                    false
                );
                manager.addEvent(event);
            }
        );
        Assertions.assertTrue(exception.getMessage().contains("title"));
    }

    @Test
    @Order(6)
    @DisplayName("Test 2.2: Add event with empty location - should throw exception")
    void testAddEventWithEmptyLocation() {
        InvalidEventException_sec33_gr3 exception = Assertions.assertThrows(
            InvalidEventException_sec33_gr3.class,
            () -> {
                CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
                    "Test Event",
                    LocalDateTime.of(2025, 12, 5, 10, 0),
                    LocalDateTime.of(2025, 12, 5, 12, 0),
                    "",
                    "Dubai",
                    "Test description",
                    "Festival",
                    false
                );
                manager.addEvent(event);
            }
        );
        Assertions.assertTrue(exception.getMessage().contains("location"));
    }

    @Test
    @Order(7)
    @DisplayName("Test 2.3: Add event with end time before start time - should throw exception")
    void testAddEventWithInvalidTimeRange() {
        InvalidEventException_sec33_gr3 exception = Assertions.assertThrows(
            InvalidEventException_sec33_gr3.class,
            () -> {
                CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
                    "Invalid Time Event",
                    LocalDateTime.of(2025, 12, 5, 18, 0),
                    LocalDateTime.of(2025, 12, 5, 10, 0), // End before start
                    "Dubai Mall",
                    "Dubai",
                    "Test event",
                    "Festival",
                    false
                );
                manager.addEvent(event);
            }
        );
        Assertions.assertTrue(exception.getMessage().contains("end time"));
    }

    @Test
    @Order(8)
    @DisplayName("Test 2.4: Add null event - should throw exception")
    void testAddNullEvent() {
        Assertions.assertThrows(InvalidEventException_sec33_gr3.class, () -> {
            manager.addEvent(null);
        });
    }

    @Test
    @Order(9)
    @DisplayName("Test 2.5: Add event with null start time - should throw exception")
    void testAddEventWithNullStartTime() {
        Assertions.assertThrows(InvalidEventException_sec33_gr3.class, () -> {
            CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
                "Test Event",
                null,
                LocalDateTime.of(2025, 12, 5, 12, 0),
                "Dubai Mall",
                "Dubai",
                "Test event",
                "Festival",
                false
            );
            manager.addEvent(event);
        });
    }

    // ==================== BASIC OPERATION 3: SEARCH EVENTS ====================

    @Test
    @Order(10)
    @DisplayName("Test 3.1: Search events by date")
    void testSearchEventsByDate() throws InvalidEventException_sec33_gr3 {
        LocalDateTime date = LocalDateTime.of(2025, 12, 10, 0, 0);
        
        CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
            "Test Event",
            LocalDateTime.of(2025, 12, 10, 15, 0),
            LocalDateTime.of(2025, 12, 10, 18, 0),
            "Dubai Mall",
            "Dubai",
            "Test event",
            "Festival",
            false
        );
        manager.addEvent(event);
        
        List<Event_sec33_gr3> results = manager.searchEventsByDate(date);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Test Event", results.get(0).getTitle());
    }

    @Test
    @Order(11)
    @DisplayName("Test 3.2: Search events by category")
    void testSearchEventsByCategory() throws InvalidEventException_sec33_gr3 {
        CharityEvent_sec33_gr3 charity1 = new CharityEvent_sec33_gr3(
            "Charity Run",
            LocalDateTime.of(2025, 12, 15, 8, 0),
            LocalDateTime.of(2025, 12, 15, 12, 0),
            "Jumeirah Beach",
            "Dubai",
            "5K charity run",
            "Dubai Cares",
            25000.0,
            "Children"
        );
        
        CulturalEvent_sec33_gr3 cultural = new CulturalEvent_sec33_gr3(
            "Music Concert",
            LocalDateTime.of(2025, 12, 16, 19, 0),
            LocalDateTime.of(2025, 12, 16, 22, 0),
            "Dubai Opera",
            "Dubai",
            "Classical music concert",
            "Performance",
            true
        );
        
        manager.addEvent(charity1);
        manager.addEvent(cultural);
        
        List<Event_sec33_gr3> charityEvents = manager.searchEventsByCategory("Charity");
        Assertions.assertEquals(1, charityEvents.size());
        Assertions.assertEquals("Charity Run", charityEvents.get(0).getTitle());
    }

    @Test
    @Order(12)
    @DisplayName("Test 3.3: Search events by emirate")
    void testSearchEventsByEmirate() throws InvalidEventException_sec33_gr3 {
        CulturalEvent_sec33_gr3 event1 = new CulturalEvent_sec33_gr3(
            "Abu Dhabi Event",
            LocalDateTime.of(2025, 12, 20, 10, 0),
            LocalDateTime.of(2025, 12, 20, 15, 0),
            "Yas Island",
            "Abu Dhabi",
            "Event in capital",
            "Festival",
            false
        );
        
        CulturalEvent_sec33_gr3 event2 = new CulturalEvent_sec33_gr3(
            "Sharjah Event",
            LocalDateTime.of(2025, 12, 21, 10, 0),
            LocalDateTime.of(2025, 12, 21, 15, 0),
            "Sharjah Heritage Area",
            "Sharjah",
            "Heritage event",
            "Exhibition",
            false
        );
        
        manager.addEvent(event1);
        manager.addEvent(event2);
        
        List<Event_sec33_gr3> abuDhabiEvents = manager.searchEventsByEmirate("Abu Dhabi");
        Assertions.assertEquals(1, abuDhabiEvents.size());
        Assertions.assertEquals("Abu Dhabi Event", abuDhabiEvents.get(0).getTitle());
    }

    @Test
    @Order(13)
    @DisplayName("Test 3.4: Search events by location (partial match)")
    void testSearchEventsByLocation() throws InvalidEventException_sec33_gr3 {
        CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
            "Mall Event",
            LocalDateTime.of(2025, 12, 25, 14, 0),
            LocalDateTime.of(2025, 12, 25, 20, 0),
            "Dubai Mall Food Court",
            "Dubai",
            "Food festival",
            "Festival",
            false
        );
        manager.addEvent(event);
        
        List<Event_sec33_gr3> results = manager.searchEventsByLocation("Mall");
        Assertions.assertEquals(1, results.size());
        Assertions.assertTrue(results.get(0).getLocation().contains("Mall"));
    }

    @Test
    @Order(14)
    @DisplayName("Test 3.5: Search with no results")
    void testSearchWithNoResults() {
        List<Event_sec33_gr3> results = manager.searchEventsByCategory("NonExistent");
        Assertions.assertTrue(results.isEmpty());
    }

    // ==================== BASIC OPERATION 4: REMOVE EVENT ====================

    @Test
    @Order(15)
    @DisplayName("Test 4.1: Remove existing event")
    void testRemoveExistingEvent() throws InvalidEventException_sec33_gr3 {
        CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
            "Event to Remove",
            LocalDateTime.of(2026, 1, 10, 10, 0),
            LocalDateTime.of(2026, 1, 10, 12, 0),
            "Test Location",
            "Dubai",
            "Test event",
            "Festival",
            false
        );
        manager.addEvent(event);
        
        outContent.reset();
        boolean removed = manager.removeEvent("Event to Remove");
        Assertions.assertTrue(removed);
        Assertions.assertTrue(outContent.toString().contains("removed successfully"));
    }

    @Test
    @Order(16)
    @DisplayName("Test 4.2: Remove non-existent event")
    void testRemoveNonExistentEvent() {
        outContent.reset();
        boolean removed = manager.removeEvent("Non Existent Event");
        Assertions.assertFalse(removed);
        Assertions.assertTrue(outContent.toString().contains("Event not found"));
    }

    @Test
    @Order(17)
    @DisplayName("Test 4.3: Remove event from time slot with multiple events")
    void testRemoveEventFromCollisionSlot() throws InvalidEventException_sec33_gr3 {
        LocalDateTime sameTime = LocalDateTime.of(2026, 2, 1, 10, 0);
        
        CulturalEvent_sec33_gr3 event1 = new CulturalEvent_sec33_gr3(
            "Event One",
            sameTime,
            LocalDateTime.of(2026, 2, 1, 12, 0),
            "Location A",
            "Dubai",
            "First event",
            "Festival",
            false
        );
        
        CulturalEvent_sec33_gr3 event2 = new CulturalEvent_sec33_gr3(
            "Event Two",
            sameTime,
            LocalDateTime.of(2026, 2, 1, 13, 0),
            "Location B",
            "Abu Dhabi",
            "Second event",
            "Exhibition",
            false
        );
        
        manager.addEvent(event1);
        manager.addEvent(event2);
        
        boolean removed = manager.removeEvent("Event One");
        Assertions.assertTrue(removed);
        
        // Event Two should still be searchable
        List<Event_sec33_gr3> results = manager.searchEventsByDate(sameTime);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Event Two", results.get(0).getTitle());
    }

    // ==================== BASIC OPERATION 2: UPDATE EVENT ====================

    @Test
    @Order(18)
    @DisplayName("Test 5.1: Update existing event")
    void testUpdateExistingEvent() throws InvalidEventException_sec33_gr3 {
        CulturalEvent_sec33_gr3 originalEvent = new CulturalEvent_sec33_gr3(
            "Original Event",
            LocalDateTime.of(2026, 3, 1, 10, 0),
            LocalDateTime.of(2026, 3, 1, 12, 0),
            "Original Location",
            "Dubai",
            "Original description",
            "Festival",
            false
        );
        manager.addEvent(originalEvent);
        
        CulturalEvent_sec33_gr3 updatedEvent = new CulturalEvent_sec33_gr3(
            "Original Event", // Same title for update
            LocalDateTime.of(2026, 3, 1, 14, 0), // New time
            LocalDateTime.of(2026, 3, 1, 16, 0),
            "New Location",
            "Abu Dhabi",
            "Updated description",
            "Exhibition",
            true
        );
        
        boolean updated = manager.updateEvent("Original Event", updatedEvent);
        Assertions.assertTrue(updated);
        
        List<Event_sec33_gr3> results = manager.searchEventsByLocation("New Location");
        Assertions.assertEquals(1, results.size());
    }

    @Test
    @Order(19)
    @DisplayName("Test 5.2: Update non-existent event")
    void testUpdateNonExistentEvent() throws InvalidEventException_sec33_gr3 {
        CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
            "New Event",
            LocalDateTime.of(2026, 4, 1, 10, 0),
            LocalDateTime.of(2026, 4, 1, 12, 0),
            "Test Location",
            "Dubai",
            "Test",
            "Festival",
            false
        );
        
        boolean updated = manager.updateEvent("Non Existent", event);
        Assertions.assertFalse(updated);
    }

    // ==================== BASIC OPERATION 5: DISPLAY EVENTS ====================

    @Test
    @Order(20)
    @DisplayName("Test 6.1: Display all events in chronological order")
    void testDisplayAllEventsChronological() throws InvalidEventException_sec33_gr3 {
        // Add events in random order
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Third Event",
            LocalDateTime.of(2026, 5, 3, 10, 0),
            LocalDateTime.of(2026, 5, 3, 12, 0),
            "Location C",
            "Sharjah",
            "Third",
            "Festival",
            false
        ));
        
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "First Event",
            LocalDateTime.of(2026, 5, 1, 10, 0),
            LocalDateTime.of(2026, 5, 1, 12, 0),
            "Location A",
            "Dubai",
            "First",
            "Festival",
            false
        ));
        
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Second Event",
            LocalDateTime.of(2026, 5, 2, 10, 0),
            LocalDateTime.of(2026, 5, 2, 12, 0),
            "Location B",
            "Abu Dhabi",
            "Second",
            "Festival",
            false
        ));
        
        outContent.reset();
        manager.displayAllEvents();
        String output = outContent.toString();
        
        Assertions.assertTrue(output.contains("CHRONOLOGICAL ORDER"));
        Assertions.assertTrue(output.contains("First Event"));
        Assertions.assertTrue(output.contains("Second Event"));
        Assertions.assertTrue(output.contains("Third Event"));
    }

    @Test
    @Order(21)
    @DisplayName("Test 6.2: Display empty schedule")
    void testDisplayEmptySchedule() {
        EventManager_sec33_gr3 emptyManager = new EventManager_sec33_gr3();
        outContent.reset();
        emptyManager.displayAllEvents();
        Assertions.assertTrue(outContent.toString().contains("No events scheduled"));
    }

    // ==================== ADDITIONAL FEATURES: VISUALIZATION ====================

    @Test
    @Order(22)
    @DisplayName("Test 7.1: Print event tree structure")
    void testPrintEventTree() throws InvalidEventException_sec33_gr3 {
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Tree Test Event",
            LocalDateTime.of(2026, 6, 1, 10, 0),
            LocalDateTime.of(2026, 6, 1, 12, 0),
            "Test Location",
            "Dubai",
            "Test tree visualization",
            "Festival",
            false
        ));
        
        outContent.reset();
        manager.displayTreeStructure();
        String output = outContent.toString();
        
        // Fixed: Match the actual output format
        assertTrue(output.contains("TREE VISUALIZATION") || output.contains("EVENT_SCHEDULE"));
        assertTrue(output.contains("├─") || output.contains("└─"));
    }





    // ==================== MAINTENANCE OPERATIONS ====================

    @Test
    @Order(26)
    @DisplayName("Test 8.1: Remove outdated events")
    void testRemoveOutdatedEvents() throws InvalidEventException_sec33_gr3 {
        // Add past event
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Past Event",
            LocalDateTime.of(2020, 1, 1, 10, 0),
            LocalDateTime.of(2020, 1, 1, 12, 0),
            "Old Location",
            "Dubai",
            "Past event",
            "Festival",
            false
        ));
        
        // Add future event
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Future Event",
            LocalDateTime.now().plusDays(10),
            LocalDateTime.now().plusDays(10).plusHours(2),
            "New Location",
            "Dubai",
            "Future event",
            "Festival",
            false
        ));
        
        outContent.reset();
        manager.removeOutdatedEvents();
        String output = outContent.toString();
        
        Assertions.assertTrue(output.contains("Removed") && output.contains("outdated"));
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    @Order(27)
    @DisplayName("Test 9.1: Complete workflow - Add, Search, Update, Remove")
    void testCompleteWorkflow() throws InvalidEventException_sec33_gr3 {
        // 1. Add event
        CulturalEvent_sec33_gr3 event = new CulturalEvent_sec33_gr3(
            "Workflow Test Event",
            LocalDateTime.of(2026, 9, 1, 10, 0),
            LocalDateTime.of(2026, 9, 1, 12, 0),
            "Test Location",
            "Dubai",
            "Workflow test",
            "Festival",
            false
        );
        manager.addEvent(event);
        
        // 2. Search for it
        List<Event_sec33_gr3> found = manager.searchEventsByCategory("Cultural");
        Assertions.assertTrue(found.stream().anyMatch(e -> e.getTitle().equals("Workflow Test Event")));
        
        // 3. Update it
        CulturalEvent_sec33_gr3 updatedEvent = new CulturalEvent_sec33_gr3(
            "Workflow Test Event",
            LocalDateTime.of(2026, 9, 2, 14, 0),
            LocalDateTime.of(2026, 9, 2, 16, 0),
            "Updated Location",
            "Abu Dhabi",
            "Updated workflow test",
            "Exhibition",
            true
        );
        boolean updated = manager.updateEvent("Workflow Test Event", updatedEvent);
        Assertions.assertTrue(updated);
        
        // 4. Verify update
        List<Event_sec33_gr3> afterUpdate = manager.searchEventsByLocation("Updated Location");
        Assertions.assertEquals(1, afterUpdate.size());
        
        // 5. Remove it
        boolean removed = manager.removeEvent("Workflow Test Event");
        Assertions.assertTrue(removed);
        
        // 6. Verify removal
        List<Event_sec33_gr3> afterRemove = manager.searchEventsByLocation("Updated Location");
        Assertions.assertTrue(afterRemove.isEmpty());
    }

    @Test
    @Order(28)
    @DisplayName("Test 9.2: Stress test - Multiple operations")
    void testStressMultipleOperations() throws InvalidEventException_sec33_gr3 {
        // Add 20 events
        for (int i = 1; i <= 20; i++) {
            manager.addEvent(new CulturalEvent_sec33_gr3(
                "Event " + i,
                LocalDateTime.of(2026, 10, i, 10, 0),
                LocalDateTime.of(2026, 10, i, 12, 0),
                "Location " + i,
                i % 2 == 0 ? "Dubai" : "Abu Dhabi",
                "Test event " + i,
                "Festival",
                false
            ));
        }
        
        // Search operations
        List<Event_sec33_gr3> dubaiEvents = manager.searchEventsByEmirate("Dubai");
        Assertions.assertEquals(10, dubaiEvents.size());
        
        // Remove some events
        manager.removeEvent("Event 5");
        manager.removeEvent("Event 10");
        manager.removeEvent("Event 15");
        
        // Verify removals
        List<Event_sec33_gr3> afterRemoval = manager.searchEventsByEmirate("Abu Dhabi");
        Assertions.assertEquals(8, afterRemoval.size()); // 10 - 2 removed = 8
    }

    // ==================== EDGE CASES ====================

    @Test
    @Order(29)
    @DisplayName("Test 10.1: Case insensitive search")
    void testCaseInsensitiveSearch() throws InvalidEventException_sec33_gr3 {
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Case Test Event",
            LocalDateTime.of(2026, 11, 1, 10, 0),
            LocalDateTime.of(2026, 11, 1, 12, 0),
            "Dubai Mall",
            "Dubai",
            "Test case sensitivity",
            "Festival",
            false
        ));
        
        List<Event_sec33_gr3> results = manager.searchEventsByEmirate("dubai");
        Assertions.assertFalse(results.isEmpty());
    }

    @Test
    @Order(30)
    @DisplayName("Test 10.2: Overlapping events at different locations")
    void testOverlappingEventsAtDifferentLocations() throws InvalidEventException_sec33_gr3 {
        LocalDateTime startTime = LocalDateTime.of(2026, 12, 1, 15, 0);
        
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Event in Dubai",
            startTime,
            LocalDateTime.of(2026, 12, 1, 18, 0),
            "Dubai Mall",
            "Dubai",
            "Dubai event",
            "Festival",
            false
        ));
        
        manager.addEvent(new CulturalEvent_sec33_gr3(
            "Event in Abu Dhabi",
            startTime,
            LocalDateTime.of(2026, 12, 1, 18, 0),
            "Yas Mall",
            "Abu Dhabi",
            "Abu Dhabi event",
            "Festival",
            false
        ));
        
        List<Event_sec33_gr3> results = manager.searchEventsByDate(startTime);
        Assertions.assertEquals(2, results.size());
    }

    @Test
    @Order(31)
    @DisplayName("Test 10.3: Event with minimum duration")
    void testEventWithMinimumDuration() throws InvalidEventException_sec33_gr3 {
        LocalDateTime start = LocalDateTime.of(2027, 1, 1, 10, 0);
        LocalDateTime end = start.plusMinutes(1); // 1 minute duration
        
        Assertions.assertDoesNotThrow(() -> {
            manager.addEvent(new CulturalEvent_sec33_gr3(
                "Short Event",
                start,
                end,
                "Test Location",
                "Dubai",
                "Very short event",
                "Festival",
                false
            ));
        });
    }
}

