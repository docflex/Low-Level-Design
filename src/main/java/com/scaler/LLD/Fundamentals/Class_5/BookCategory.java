package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: BookCategory — Enum
 *
 * PRD says: "Books have categories: Fiction, Non-Fiction, Academic."
 * Enums enforce a fixed set of valid values — no typos, no ambiguity.
 *
 * Without enums:
 *   String category = "fiction";   // "Fiction"? "FICTION"? "FicTion"?
 *
 * With enums:
 *   BookCategory category = BookCategory.FICTION;  // only valid values allowed
 *
 * Real-world examples of enums:
 *   - E-commerce: OrderStatus (PENDING, SHIPPED, DELIVERED)
 *   - Payments: PaymentMethod (CREDIT_CARD, UPI, NET_BANKING)
 *   - UI: Dropdowns are backed by enums
 */
public enum BookCategory {
    FICTION,
    NON_FICTION,
    ACADEMIC
}
