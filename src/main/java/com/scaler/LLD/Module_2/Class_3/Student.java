package com.scaler.LLD.Module_2.Class_3;

/**
 * Final, production-grade Builder from the class.
 *
 * - private constructor      -> the only way in is build(), so validation can't be skipped
 * - static nested builder    -> Student.getBuilder() works without a Student object
 * - setters return this      -> calls can be chained
 * - final fields, no setters -> a built Student can't be changed afterwards
 *
 * See BuilderEvolutionDemo for every step on the way here.
 */
public class Student {
    private final long id;
    private final int age;
    private final double psp;
    private final String name;
    private final String batch;
    private final int graduationYear;
    private final String phoneNumber;
    private final String universityName;

    public static StudentBuilder getBuilder() {
        return new StudentBuilder();
    }

    private Student(StudentBuilder builder) {
        this.id = builder.getId();
        this.age = builder.getAge();
        this.psp = builder.getPsp();
        this.name = builder.getName();
        this.batch = builder.getBatch();
        this.graduationYear = builder.getGraduationYear();
        this.phoneNumber = builder.getPhoneNumber();
        this.universityName = builder.getUniversityName();
    }

    public long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public double getPsp() {
        return psp;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUniversityName() {
        return universityName;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age + ", psp=" + psp
                + ", batch='" + batch + "', graduationYear=" + graduationYear
                + ", phoneNumber='" + phoneNumber + "', universityName='" + universityName + "'}";
    }

    public static class StudentBuilder {
        private long id;
        private int age;
        private double psp;
        private String name;
        private String batch;
        private int graduationYear;
        private String phoneNumber;
        private String universityName;

        public long getId() {
            return id;
        }

        public StudentBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public int getAge() {
            return age;
        }

        public StudentBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public double getPsp() {
            return psp;
        }

        public StudentBuilder setPsp(double psp) {
            this.psp = psp;
            return this;
        }

        public String getName() {
            return name;
        }

        public StudentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public String getBatch() {
            return batch;
        }

        public StudentBuilder setBatch(String batch) {
            this.batch = batch;
            return this;
        }

        public int getGraduationYear() {
            return graduationYear;
        }

        public StudentBuilder setGraduationYear(int graduationYear) {
            this.graduationYear = graduationYear;
            return this;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public StudentBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public String getUniversityName() {
            return universityName;
        }

        public StudentBuilder setUniversityName(String universityName) {
            this.universityName = universityName;
            return this;
        }

        public Student build() {
            validate();
            return new Student(this);
        }

        private void validate() {
            if (this.graduationYear < 2022) {
                throw new IllegalArgumentException("Graduation Year Cannot Be less than 2022");
            }
            // Add more here
        }
    }
}
