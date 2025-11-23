# 🚀 Advanced Feature Explanation: Region & Category-Based TreeMaps

## Overview
This implements the **"Handling Large Event Datasets"** bonus requirement by using **multiple specialized TreeMaps** to organize events efficiently by **emirate (region)** and **category**, achieving **O(log n)** operations instead of **O(n)** linear scans.

---

## 📊 Data Structure Design

### **Main Structure Declaration**
```java
// ADVANCED FEATURE: Region-based TreeMaps for O(log n) emirate-specific operations
private Map<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> regionBasedTrees;

// ADVANCED FEATURE: Category-based TreeMaps for O(log n) category-specific operations
private Map<String, TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>>> categoryBasedTrees;
```

### **Breaking Down the Structure:**

```
regionBasedTrees = HashMap<String, TreeMap<LocalDateTime, ArrayList<Event>>>
                          ↓             ↓                  ↓
                    Emirate Name    Time-sorted      Events at that
                    (e.g., "Dubai")  Red-Black Tree   exact time
```

#### **Visual Representation:**

```
regionBasedTrees (HashMap)
├── "Dubai" → TreeMap (Red-Black Tree sorted by time)
│   ├── 2025-11-25 09:00 → ArrayList [Event1, Event2]
│   ├── 2025-11-28 19:00 → ArrayList [Event3]
│   └── 2025-12-02 18:00 → ArrayList [Event4]
│
├── "Abu Dhabi" → TreeMap
│   ├── 2025-11-27 14:00 → ArrayList [Event5]
│   └── 2025-12-05 10:00 → ArrayList [Event6, Event7]
│
├── "Sharjah" → TreeMap
│   ├── 2025-12-03 10:00 → ArrayList [Event8]
│   └── 2025-12-15 16:00 → ArrayList [Event9]
│
└── ... (other emirates)
```

### **Why This Design?**

1. **HashMap for emirate/category lookup** → O(1) average time
2. **TreeMap for chronological ordering** → O(log n) insert/search
3. **ArrayList for handling collisions** → Multiple events at same time

**Total complexity for region-specific search: O(1) + O(k)** where k = events in that emirate
- Much faster than O(n) full scan when you have thousands of events!

---

## 🏗️ Initialization in Constructor

```java
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
```

### **Step-by-Step Explanation:**

#### **Step 1: Create the outer HashMap**
```java
regionBasedTrees = new HashMap<>();
```
- Creates an empty HashMap that will map emirate names to TreeMaps

#### **Step 2: Pre-create TreeMaps for each emirate**
```java
for (String emirate : emirates) {
    regionBasedTrees.put(emirate, new TreeMap<>());
}
```

**After this loop, memory looks like:**
```
regionBasedTrees:
  "Abu Dhabi"      → Empty TreeMap (ready to receive events)
  "Dubai"          → Empty TreeMap
  "Sharjah"        → Empty TreeMap
  "Ajman"          → Empty TreeMap
  "Umm Al Quwain"  → Empty TreeMap
  "Ras Al Khaimah" → Empty TreeMap
  "Fujairah"       → Empty TreeMap
  "Al Ain"         → Empty TreeMap
```

**Why pre-create?**
- Avoids null checks later
- Ensures consistent structure
- Ready for immediate use

#### **Step 3: Same for categories**
```java
categoryBasedTrees.put("Cultural", new TreeMap<>());
categoryBasedTrees.put("Educational", new TreeMap<>());
categoryBasedTrees.put("Charity", new TreeMap<>());
```

---

## ➕ Adding Events to Region Tree

```java
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
```

### **Line-by-Line Breakdown:**

#### **Line 1-2: Validation and Extract Emirate**
```java
validateEvent(event);
String emirate = event.getEmirate();  // e.g., "Dubai"
```
- Ensures event is valid
- Gets the emirate name (e.g., "Dubai", "Abu Dhabi")

#### **Line 3: Get the Specific TreeMap for This Emirate**
```java
TreeMap<LocalDateTime, ArrayList<Event_sec33_gr3>> regionTree = regionBasedTrees.get(emirate);
```

**Example:** If `emirate = "Dubai"`, this retrieves Dubai's TreeMap:
```
regionTree = Dubai's TreeMap
  2025-11-25 09:00 → [Event A, Event B]
  2025-12-02 18:00 → [Event C]
```

**Time Complexity:** O(1) - HashMap lookup

#### **Line 4-7: Safety Check (Handle Unknown Emirates)**
```java
if (regionTree == null) {
    regionTree = new TreeMap<>();
    regionBasedTrees.put(emirate, regionTree);
}
```
- If someone adds an event for an emirate not in our initial list
- Creates a new TreeMap on-the-fly
- This makes the system flexible and extensible

#### **Line 9: Get Event Start Time**
```java
LocalDateTime startTime = event.getStartTime();  // e.g., 2025-12-02 18:00
```

#### **Line 10-17: Add Event to the Time-Sorted Tree**

**Case 1: Time slot already exists (collision)**
```java
if (regionTree.containsKey(startTime)) {
    regionTree.get(startTime).add(event);  // Add to existing ArrayList
}
```

**Visual Example:**
```
BEFORE:
Dubai's TreeMap:
  2025-12-02 18:00 → [Event A, Event B]

Adding Event C at 2025-12-02 18:00

AFTER:
Dubai's TreeMap:
  2025-12-02 18:00 → [Event A, Event B, Event C]  ← C added to ArrayList
```

**Case 2: New time slot**
```java
else {
    ArrayList<Event_sec33_gr3> eventList = new ArrayList<>();
    eventList.add(event);
    regionTree.put(startTime, eventList);  // Create new TreeMap node
}
```

**Visual Example:**
```
BEFORE:
Dubai's TreeMap:
  2025-11-25 09:00 → [Event A]
  2025-12-02 18:00 → [Event B]

Adding Event C at 2025-11-30 06:00

AFTER:
Dubai's TreeMap:
  2025-11-25 09:00 → [Event A]
  2025-11-30 06:00 → [Event C]  ← New node inserted (Red-Black rebalancing)
  2025-12-02 18:00 → [Event B]
```

**Time Complexity:** O(log n) - Red-Black Tree insertion

---

## 🎯 Complete Example: Adding Multiple Events

### **Scenario: Adding 3 events to Dubai**

```java
Event event1 = new CulturalEvent("Dubai Opera", 2025-12-02 18:00, ...);
Event event2 = new EducationalEvent("AI Workshop", 2025-11-25 09:00, ...);
Event event3 = new CharityEvent("Charity Run", 2025-12-02 18:00, ...);  // Same time as event1
```

#### **Step 1: Add event1 (Dubai Opera)**
```
emirate = "Dubai"
startTime = 2025-12-02 18:00
regionTree = Dubai's TreeMap (empty)

Action: Create new time slot
Result:
  Dubai TreeMap:
    2025-12-02 18:00 → [Dubai Opera]
```

#### **Step 2: Add event2 (AI Workshop)**
```
emirate = "Dubai"
startTime = 2025-11-25 09:00
regionTree = Dubai's TreeMap (has 1 node)

Action: Create new time slot (earlier date)
Result:
  Dubai TreeMap:
    2025-11-25 09:00 → [AI Workshop]     ← Inserted here (earlier)
    2025-12-02 18:00 → [Dubai Opera]
```

#### **Step 3: Add event3 (Charity Run) - COLLISION**
```
emirate = "Dubai"
startTime = 2025-12-02 18:00  ← Same as event1!
regionTree = Dubai's TreeMap (has 2 nodes)

Action: Add to existing ArrayList (collision handling)
Result:
  Dubai TreeMap:
    2025-11-25 09:00 → [AI Workshop]
    2025-12-02 18:00 → [Dubai Opera, Charity Run]  ← Both events at same time!
```

---

## 🔍 Benefits of This Approach

### **1. Efficient Regional Queries - O(1) + O(k)**
```java
// Get all Dubai events
List<Event> dubaiEvents = eventManager.getEventsByRegion("Dubai");
```
**Without regionBasedTrees:** O(n) - scan all events
**With regionBasedTrees:** O(1) HashMap lookup + O(k) where k = Dubai events only

**Example:** If you have 10,000 total events but only 500 in Dubai:
- Old way: Check all 10,000 events
- New way: Only check 500 Dubai events ✅ **20x faster!**

### **2. Maintains Chronological Order**
Events within each emirate are automatically sorted by time (Red-Black Tree property)

### **3. Scalability**
As dataset grows to thousands of events across 7 emirates:
- Each emirate tree remains balanced (Red-Black property)
- Operations stay O(log n) within each region
- Never degrades to O(n) linear search

### **4. Regional Analytics**
```java
eventManager.displayRegionalStatistics();
```
Can quickly generate reports like:
```
Events per Emirate:
Dubai          : 150 ██████████████████
Abu Dhabi      : 120 ███████████████
Sharjah        :  80 ██████████
```

---

## 📈 Time Complexity Summary

| Operation | Without Region Trees | With Region Trees |
|-----------|---------------------|-------------------|
| Add event | O(log n) | O(log n) + O(log k) ≈ O(log n) |
| Search by emirate | O(n) | O(k) where k << n |
| Display by region | O(n) | O(k) per region |
| Regional statistics | O(n) | O(r) where r = number of regions |

**Key: n = total events, k = events in specific region, r = number of regions**

---

## 🎓 Why This Meets "Handling Large Datasets" Requirement

✅ **Uses multiple TreeMaps** (region-based and category-based)
✅ **Efficiently manages high-volume events** across multiple Emirates
✅ **O(log n) operations** maintained through Red-Black Trees
✅ **Scalable architecture** - doesn't degrade with dataset size
✅ **Supports JSON loading** for large external datasets
✅ **Provides analytical insights** (regional/category statistics)

---

## 🔗 Integration with Main System

When you call:
```java
eventManager.addEvent(event);
```

Behind the scenes, the event is added to **THREE** data structures simultaneously:
1. **Main eventSchedule** (global chronological view)
2. **regionBasedTrees[emirate]** (regional organization)
3. **categoryBasedTrees[category]** (category organization)

This **triple indexing** enables lightning-fast queries from multiple perspectives! 🚀

