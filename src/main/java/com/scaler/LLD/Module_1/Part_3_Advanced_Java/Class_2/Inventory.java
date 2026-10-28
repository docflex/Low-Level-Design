package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Inventory<T extends Item> {
    private final HashMap<String, T> store;

    public Inventory() {
        this.store = new HashMap<>();
    }

    public void addItem(T item) {
        if (item.getQuantity() < 0) {
            throw new IllegalStateException("Quantity Cannot be Less than 0");
        }
        if (store.containsKey(item.getId())) {
            throw new IllegalStateException("Item with Id " + item.getId() + " already present");
        }
        store.put(item.getId(), item);
    }

    public void removeItem(String id) {
        store.remove(id);
    }

    public T getItem(String id) {
        return store.get(id);
    }

    public Collection<T> getAllItems() {
        return store.values();
    }

    // ── Milestone 5: Filtering ──

    public List<T> filterByPriceRange(double minPrice, double maxPrice) {
        List<T> result = new ArrayList<>();
        for (T item : store.values()) {
            if (item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
                result.add(item);
            }
        }
        return result;
    }

    public List<T> filterByAvailability() {
        List<T> result = new ArrayList<>();
        for (T item : store.values()) {
            if (item.getQuantity() > 0) {
                result.add(item);
            }
        }
        return result;
    }
}
