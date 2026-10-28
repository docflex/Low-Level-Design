package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Java Advanced Concepts - 2 [Generics & Collections Lab]
 *
 * Inventory Management System demo covering:
 * - M1: Item hierarchy + Comparable (natural ordering by name)
 * - M2: Generic Inventory&lt;T extends Item&gt; with HashMap
 * - M3: RecentlyViewedItems with LinkedList (LRU-style)
 * - M4: OrderQueue with PriorityQueue + Comparator (express first)
 * - Comparator for custom sort (price ASC via ItemPriceComparator)
 */
public class IMSDemo {
    public static void main(String[] args) {

        // ── Milestone 1: Item Hierarchy + Comparable ──
        System.out.println("=== M1: Comparable (natural order by name) ===");

        Electronics e1 = new Electronics("1", "iPhone 18", 179_000, 2, 2);
        Clothing c1 = new Clothing("2", "Supreme BoGo", 7_500, 10, "S");
        Book b1 = new Book("3", "DDIA", 1_500, 100, "Klepmann");

        List<Item> items = new ArrayList<>();
        items.add(e1);
        items.add(c1);
        items.add(b1);

        Collections.sort(items);
        System.out.println("Sorted by name: " + items);

        // ── Milestone 2: Generic Inventory ──
        System.out.println("\n=== M2: Inventory<Item> (HashMap) ===");

        Inventory<Item> itemInventory = new Inventory<>();
        itemInventory.addItem(e1);
        itemInventory.addItem(c1);
        itemInventory.addItem(b1);

        System.out.println("Get id=1: " + itemInventory.getItem("1"));
        System.out.println("Get id=2: " + itemInventory.getItem("2"));
        System.out.println("Get id=3: " + itemInventory.getItem("3"));
        System.out.println("All items: " + itemInventory.getAllItems());

        itemInventory.removeItem("2");
        System.out.println("After removing id=2: " + itemInventory.getAllItems());

        // ── Milestone 3: Recently Viewed Items ──
        System.out.println("\n=== M3: RecentlyViewedItems (LinkedList, max 10) ===");

        RecentlyViewedItems recents = new RecentlyViewedItems();

        for (int i = 1; i <= 10; i++) {
            recents.addRecentlyViewedItem(new Item("A-" + i, "Test Item " + i, 10, 10));
        }

        System.out.println("After adding 10: size=" + recents.getRecents().size());

        recents.addRecentlyViewedItem(new Item("A-11", "Test Item 11", 10, 10));
        System.out.println("After adding 11th: size=" + recents.getRecents().size());
        System.out.println("First (most recent): " + recents.getRecents().get(0));
        System.out.println("Last (oldest):       " + recents.getRecents().get(recents.getRecents().size() - 1));

        // ── Comparator: Sort by price ──
        System.out.println("\n=== Comparator (sort by price ASC) ===");

        List<Item> items2 = new ArrayList<>();
        items2.add(e1);
        items2.add(c1);
        items2.add(b1);

        Collections.sort(items2, new ItemPriceComparator());
        System.out.println("Sorted by price: " + items2);

        // PriorityQueue with Comparator
        System.out.println("\n=== PriorityQueue (price order) ===");

        PriorityQueue<Item> priorityQueue = new PriorityQueue<>(new ItemPriceComparator());
        priorityQueue.add(e1);
        priorityQueue.add(c1);
        priorityQueue.add(b1);

        while (!priorityQueue.isEmpty()) {
            System.out.println("  poll: " + priorityQueue.poll());
        }

        // ── Milestone 4: Order Queue (express first) ──
        System.out.println("\n=== M4: OrderQueue (express orders first) ===");

        OrderQueue orderQueue = new OrderQueue();
        orderQueue.addOrder(new Order("123", true));
        orderQueue.addOrder(new Order("456", false));
        orderQueue.addOrder(new Order("789", true));
        orderQueue.addOrder(new Order("000", false));

        System.out.println("  " + orderQueue.processOrder());
        System.out.println("  " + orderQueue.processOrder());
        System.out.println("  " + orderQueue.processOrder());
        System.out.println("  " + orderQueue.processOrder());
    }
}
