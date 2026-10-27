package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_1;

import java.util.*;

/**
 * Java Advanced Concepts - 1 [Generics]
 *
 * Demonstrates the Java Collections Framework hierarchy:
 * - List: ArrayList, LinkedList
 * - Set: HashSet (unordered), LinkedHashSet (insertion order), TreeSet (sorted)
 * - Map: HashMap (unordered), LinkedHashMap (insertion order), TreeMap (sorted keys)
 * - Queue: PriorityQueue (min-heap), ArrayDeque (stack/queue)
 *
 * All use generics internally (e.g., List&lt;T&gt;, Map&lt;K,V&gt;).
 */
public class CollectionsDemo {
    public static void main(String[] args) {

        // ── List: ordered, allows duplicates ──
        System.out.println("=== List (ArrayList vs LinkedList) ===");
        List<String> arrayList = new ArrayList<>(List.of("C", "A", "B", "A"));
        List<String> linkedList = new LinkedList<>(List.of("C", "A", "B", "A"));
        System.out.println("  ArrayList:  " + arrayList);
        System.out.println("  LinkedList: " + linkedList);

        // ── Set: unique elements ──
        System.out.println("\n=== Set (HashSet vs LinkedHashSet vs TreeSet) ===");
        Set<String> hashSet = new HashSet<>(List.of("Banana", "Apple", "Cherry", "Apple"));
        Set<String> linkedHashSet = new LinkedHashSet<>(List.of("Banana", "Apple", "Cherry"));
        Set<String> treeSet = new TreeSet<>(List.of("Banana", "Apple", "Cherry"));
        System.out.println("  HashSet (unordered):            " + hashSet);
        System.out.println("  LinkedHashSet (insertion order): " + linkedHashSet);
        System.out.println("  TreeSet (sorted):               " + treeSet);

        // ── Map: key-value pairs ──
        System.out.println("\n=== Map (HashMap vs LinkedHashMap vs TreeMap) ===");
        Map<String, Double> hashMap = new HashMap<>();
        hashMap.put("Rehber", 87.5); hashMap.put("Varun", 77.2); hashMap.put("Rahul", 92.0);

        Map<String, Double> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Rehber", 87.5); linkedHashMap.put("Varun", 77.2); linkedHashMap.put("Rahul", 92.0);

        Map<String, Double> treeMap = new TreeMap<>();
        treeMap.put("Rehber", 87.5); treeMap.put("Varun", 77.2); treeMap.put("Rahul", 92.0);

        System.out.println("  HashMap (unordered):             " + hashMap);
        System.out.println("  LinkedHashMap (insertion order):  " + linkedHashMap);
        System.out.println("  TreeMap (sorted keys):           " + treeMap);

        // ── Queue and Deque ──
        System.out.println("\n=== Queue (PriorityQueue) and Deque (ArrayDeque) ===");
        Queue<Integer> pq = new PriorityQueue<>(List.of(30, 10, 20, 5));
        System.out.print("  PriorityQueue poll order: ");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");
        }
        System.out.println();

        Deque<String> deque = new ArrayDeque<>();
        deque.push("First");
        deque.push("Second");
        deque.push("Third");
        System.out.print("  ArrayDeque as stack (LIFO): ");
        while (!deque.isEmpty()) {
            System.out.print(deque.pop() + " ");
        }
        System.out.println();

        // ── Why Map is separate from Collection ──
        System.out.println("\n=== Why Map is NOT in Collection hierarchy ===");
        System.out.println("  Collection<E> has 1 type param; Map<K,V> has 2.");
        System.out.println("  Map is a collection data structure, but not a Collection interface.");

        // ── Generics under the hood ──
        System.out.println("\n=== Type Erasure ===");
        List<Integer> intList = new ArrayList<>();
        intList.add(42);
        List<String> strList = new ArrayList<>();
        strList.add("hello");
        System.out.println("  intList.getClass() == strList.getClass()? "
                + (intList.getClass() == strList.getClass()));
        System.out.println("  Both are: " + intList.getClass().getName());
        System.out.println("  At runtime, <Integer> and <String> are erased -> just ArrayList");
    }
}
