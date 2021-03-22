package com.dkit.oopca5.core;

/**
 * Author: Cían Fearn
 * Student: D00228000
 * This class is just a base class for the students
 * which was provided with the project brief.
 * It only contains a constructor, a copy constructor,
 * getters, setters and a toString method
 */
public class Student
{
    private int caoNumber;  // In the CAO system, cao number is unique identifier for student
    private String dateOfBirth; // yyyy-mm-dd
    private String password;    // min 8 characters
    private String email;

    // Copy Constructor
    // Copies the contents of a Student object argument into
    // a new Student object, and returns that new object (a clone)
    //Copy constructor - will copy all of the attributes over to the new object
    public Student(Student student)
    {
        this.caoNumber = student.caoNumber;
        this.dateOfBirth = student.dateOfBirth;
        this.password = student.password;
        this.email = student.email;
    }

    // Constructor
    public Student(int caoNumber, String dateOfBirth, String password, String email) {
        this.caoNumber = caoNumber;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.email = email;
    }

    public int getCaoNumber() {
        return caoNumber;
    }

    public void setCaoNumber(int caoNumber) {
        this.caoNumber = caoNumber;
    }

    public String getDayOfBirth() {
        return dateOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dateOfBirth = dayOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CAO number: " + caoNumber +"\n"+"Date of birth: " + dateOfBirth +
                "\n" +"password: " + password + "\n" + "email: " + email;
    }
}
