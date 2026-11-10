package com.scaler.LLD.Module_2.Class_3.instructor;

/**
 * Practice problem ("Extra Stuff"): an Instructor built with a Builder.
 * Rules: must have a name, must be at least 28, must be a working professional.
 * "Working professional" is interpreted as: currently works at a company (company is not blank).
 */
public class Instructor {
    private final String name;
    private final String email;
    private final String company;
    private final double salary;
    private final int age;

    private Instructor(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.company = builder.company;
        this.salary = builder.salary;
        this.age = builder.age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Instructor{name='" + name + "', email='" + email + "', company='" + company
                + "', salary=" + salary + ", age=" + age + "}";
    }

    /** No getters needed: Instructor's constructor can read the nested class's private fields directly. */
    public static class Builder {
        private String name;
        private String email;
        private String company;
        private double salary;
        private int age;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Instructor build() {
            validate();
            return new Instructor(this);
        }

        private void validate() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Instructor must have a name");
            }
            if (age < 28) {
                throw new IllegalArgumentException("Instructor must be at least 28, got " + age);
            }
            if (company == null || company.isBlank()) {
                throw new IllegalArgumentException("Instructor must be a working professional (company is required)");
            }
        }
    }
}
