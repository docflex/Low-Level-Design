package com.scaler.LLD.Module_2.Class_3;

/** Uses the final Student builder: chaining, optional fields, and validation in build(). */
public class Client {
    public static void main(String[] args) {
        System.out.println("=== 1. All fields, chained ===");
        Student full = Student.getBuilder()
                .setId(1234)
                .setAge(23)
                .setPsp(93)
                .setName("Rehber")
                .setBatch("DS/ML")
                .setGraduationYear(2024)
                .setPhoneNumber("1234567890")
                .setUniversityName("ABC")
                .build();
        System.out.println("  " + full);

        System.out.println("\n=== 2. Only the fields we care about (others keep defaults) ===");
        Student student = Student.getBuilder()
                .setId(1234)
                .setName("Rehber")
                .setGraduationYear(2024)
                .build();
        System.out.println("  " + student);

        System.out.println("\n=== 3. Validation runs inside build() ===");
        try {
            Student.getBuilder()
                    .setId(5678)
                    .setName("Aman")
                    .setGraduationYear(2020)
                    .build();
            System.out.println("  created -- validation did not run!");
        } catch (IllegalArgumentException e) {
            System.out.println("  rejected: " + e.getMessage());
        }
        // new Student(...)  -> does not compile: the constructor is private, so build() can't be bypassed
    }
}
