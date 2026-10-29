package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Stream API Tasks -- practical stream pipeline exercises.
 * Covers map, filter, anyMatch, allMatch, reduce, and chained pipelines.
 */
public class Client {
    public static void main(String[] args) {
        List<Item> itemList1 = new ArrayList<>();

        itemList1.add(new Electronics("E1", "Laptop", 55_000, 5, 24));
        itemList1.add(new Electronics("E2", "Mobile", 22_000, 8, 12));
        itemList1.add(new Electronics("E3", "Earphone", 1_500, 0, 6));
        itemList1.add(new Book("B1", "Java", 799, 10, "James Gosling"));
        itemList1.add(new Clothing("C1", "T-Shirt", 499, 20, "M"));

        // === Task 1: Mapping and Transformation ===
        System.out.println("=== 1a: Print all Item IDs ===");
        itemList1.stream()
                .forEach(item -> System.out.println("  Item ID: " + item.getId()));

        // Alternatively collect into a list
        List<String> itemIds = itemList1.stream()
                .map(x -> x.getId())
                .toList();
        System.out.println("As list: " + itemIds);

        System.out.println("\n=== 1b: Item names, lowercase ===");
        itemList1.stream()
                .forEach(item -> System.out.println("  " + item.getName().toLowerCase()));

        // === Task 2: Filtering and Matching ===
        System.out.println("\n=== 2a: Items priced above 1,000 ===");
        itemList1.stream()
                .filter(x -> x.getPrice() > 1000)
                .forEach(x -> System.out.println("  " + x.getName()));

        System.out.println("\n=== 2b: Is any item out of stock? ===");
        boolean isAnyOutOfStock = itemList1.stream()
                .anyMatch(x -> x.getQuantity() <= 0);
        System.out.println("  Any OOS: " + isAnyOutOfStock);  // true (Earphone qty=0)

        System.out.println("\n=== 2c: Confirm no item has negative quantity ===");
        boolean noneHaveNegativeQuantity = itemList1.stream()
                .allMatch(x -> x.getQuantity() >= 0);
        // Alternatively: .noneMatch(x -> x.getQuantity() < 0)
        System.out.println("  All non-negative: " + noneHaveNegativeQuantity);  // true

        System.out.println("\n=== 2d: How many items priced above 1,000? ===");
        long countAbove1000 = itemList1.stream()
                .filter(x -> x.getPrice() > 1_000)
                .count();
        System.out.println("  Count: " + countAbove1000);  // 3

        // === Task 3: Chained Pipeline ===
        System.out.println("\n=== 3: Chained pipeline (filter + map + distinct + sorted + limit) ===");
        List<String> output = itemList1.stream()
                .filter(x -> x.getPrice() >= 1_000 && x.getQuantity() > 0)
                .map(x -> x.getName())
                .distinct()
                .sorted()  // natural order (String is Comparable)
                .limit(5)
                .toList();
        System.out.println("  Top 5 in-stock items > 1000: " + output);

        // === Task 4: Reduce for Aggregation ===
        System.out.println("\n=== 4a: Total quantity of all items ===");
        int totalQuantity = itemList1.stream()
                .map(x -> x.getQuantity())
                .reduce(0, (totQty, qty) -> totQty + qty);
        System.out.println("  Total quantity: " + totalQuantity);  // 43

        System.out.println("\n=== 4b: Most expensive item (reduce with identity) ===");
        double highestPrice = itemList1.stream()
                .map(x -> x.getPrice())
                .reduce(0.0, Math::max);
        System.out.println("  Max price (with identity): " + highestPrice);  // 55000.0

        // Without identity -- returns Optional (empty stream = empty Optional)
        Optional<Double> highestPrice2 = itemList1.stream()
                .map(x -> x.getPrice())
                .reduce(Math::max);
        System.out.println("  Max price (Optional): " + highestPrice2.orElse(0.0));

        System.out.println("\n=== 4c: Concatenate item names (kebab-style) ===");
        // Using Collectors.joining to avoid the leading-dash bug from
        // reduce("", (a,b) -> a + "-" + b) which produces "-Laptop-Mobile-..."
        String nameKebab = itemList1.stream()
                .map(x -> x.getName())
                .collect(Collectors.joining("-"));
        System.out.println("  Kebab: " + nameKebab);
    }
}
