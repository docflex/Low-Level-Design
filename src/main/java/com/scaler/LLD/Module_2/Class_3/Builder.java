package com.scaler.LLD.Module_2.Class_3;

/**
 * "Productionizing" step 1 from the class: the Helper renamed to Builder, with private fields
 * and plain getters/setters. Setters return void, so there's no chaining yet, and the client
 * has to know about this separate class. Used by BuilderEvolutionDemo (step 5);
 * the final version is Student.StudentBuilder.
 */
public class Builder {
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

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPsp() {
        return psp;
    }

    public void setPsp(double psp) {
        this.psp = psp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
