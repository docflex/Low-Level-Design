package com.scaler.LLD.Fundamentals.Class_5;

public class TestUser {
    public static void main(String[] args) {

        Member m1 = new Member("Rehber", "1234");

        Member m2 = new Member();
        m2.setName("Rahul");
        m2.setContactInfo("9876");

        Member m3 = new Member(m2);

        Librarian l1 = new Librarian("123", "Rohit", "123453");

        System.out.println("\n========== USER DETAILS ==========");
        System.out.println("Total users created: " + User.getTotalUsers());

        System.out.println("\n========== USER DASHBOARDS ==========");
        m1.displayDashboard();
        l1.displayDashboard();

        TextBook t1 = new TextBook(
                "1234", "DDIA", "Kleppmann", "Systems Design", 1
        );

        System.out.println("\n========== BOOK DETAILS ==========");
        t1.displayBookDetails();

        System.out.println("\n========== BOOK LENDING TESTS ==========");

        System.out.println("\n1. Check book availability before lending:");
        System.out.println("Is book available? " + t1.isAvailable());

        System.out.println("\n2. Lend book to Member: " + m1);
        System.out.println("Lending successful? " + t1.lend(m1));

        System.out.println("\n3. Check availability after lending:");
        System.out.println("Is book available? " + t1.isAvailable());

        System.out.println("\n4. Attempt to lend the same book to another member:");
        System.out.println("Lending successful? " + t1.lend(m2));

        System.out.println("\n5. Return the book:");
        t1.returnItem(m1);
        System.out.println("Is book available after return? " + t1.isAvailable());

        System.out.println("\n6. Lend the returned book to another member:");
        System.out.println("Lending successful? " + t1.lend(m2));
        System.out.println("Is book available? " + t1.isAvailable());

        System.out.println("\n7. Attempt to lend the already-issued book to a third member:");
        System.out.println("Lending successful? " + t1.lend(m3));

        System.out.println("\n========== TESTS COMPLETED ==========");
    }
}
