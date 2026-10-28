package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Java Advanced Concepts - 2 [Generics & Collections Lab]
 *
 * Milestones 5 & 6:
 * - M5: Sorting with Comparators + Inventory filtering by price range / availability
 * - M6: Wishlist using Set (duplicate detection via equals/hashCode on id)
 */
public class FilterWishlistDemo {
    public static void main(String[] args) {

        // ── Setup items ──
        Electronics e1 = new Electronics("1", "iPhone 18", 179_000, 2, 2);
        Clothing c1 = new Clothing("2", "Supreme BoGo", 7_500, 10, "S");
        Book b1 = new Book("3", "DDIA", 1_500, 100, "Klepmann");
        Item outOfStock = new Item("4", "Vintage Poster", 3_000, 0);

        Inventory<Item> inventory = new Inventory<>();
        inventory.addItem(e1);
        inventory.addItem(c1);
        inventory.addItem(b1);
        inventory.addItem(outOfStock);

        // ── M5: Custom Comparators ──
        System.out.println("=== M5: Sort by price (Comparator) ===");
        List<Item> byPrice = new ArrayList<>(inventory.getAllItems());
        Collections.sort(byPrice, new ItemPriceComparator());
        for (Item item : byPrice) {
            System.out.println("  " + item);
        }

        System.out.println("\n=== M5: Sort by quantity (Comparator) ===");
        List<Item> byQty = new ArrayList<>(inventory.getAllItems());
        Collections.sort(byQty, new ItemQuantityComparator());
        for (Item item : byQty) {
            System.out.println("  " + item);
        }

        // ── M5: Filtering ──
        System.out.println("\n=== M5: Filter by price range [1000, 10000] ===");
        List<Item> inRange = inventory.filterByPriceRange(1_000, 10_000);
        for (Item item : inRange) {
            System.out.println("  " + item);
        }

        System.out.println("\n=== M5: Filter by availability (quantity > 0) ===");
        List<Item> available = inventory.filterByAvailability();
        System.out.println("  Available: " + available.size() + " / " + 4);
        for (Item item : available) {
            System.out.println("  " + item);
        }

        // ── M6: Wishlist (Set-based uniqueness) ──
        System.out.println("\n=== M6: Wishlist (Set) ===");
        Wishlist wishlist = new Wishlist();

        System.out.println("Add iPhone 18:    " + wishlist.addToWishlist(e1));
        System.out.println("Add DDIA:         " + wishlist.addToWishlist(b1));
        System.out.println("Add iPhone again: " + wishlist.addToWishlist(new Item("1", "iPhone 18", 179_000, 2)));
        System.out.println("Size (should be 2): " + wishlist.size());
        System.out.println(wishlist);

        System.out.println("Remove DDIA:      " + wishlist.removeFromWishlist(b1));
        System.out.println("Size (should be 1): " + wishlist.size());
        System.out.println(wishlist);
    }
}
