package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Milestone 6: Wishlist using a Set for uniqueness.
 *
 * Uses LinkedHashSet to maintain insertion order while
 * rejecting duplicates (relies on Item's equals/hashCode by id).
 */
public class Wishlist {
    private final Set<Item> items;

    public Wishlist() {
        this.items = new LinkedHashSet<>();
    }

    public boolean addToWishlist(Item item) {
        return items.add(item);
    }

    public boolean removeFromWishlist(Item item) {
        return items.remove(item);
    }

    public Set<Item> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return "Wishlist{" + items + "}";
    }
}
