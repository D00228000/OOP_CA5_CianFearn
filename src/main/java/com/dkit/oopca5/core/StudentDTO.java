package com.dkit.oopca5.core;

import java.util.Objects;

/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */
public class StudentDTO
{
    private int caoNumber;  // In the CAO system, cao number is unique identifier for student
    private String dateOfBirth; // yyyy-mm-dd
    private String password;    // min 8 characters
    private String email;

    // Copy Constructor
    // Copies the contents of a Student object argument into
    // a new Student object, and returns that new object (a clone)
    //Copy constructor - will copy all of the attributes over to the new object
    public StudentDTO(StudentDTO student)
    {
        this.caoNumber = student.caoNumber;
        this.dateOfBirth = student.dateOfBirth;
        this.password = student.password;
        this.email = student.email;
    }

    // Constructor
    public StudentDTO(int caoNumber, String dateOfBirth, String password, String email) {
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

    @Override   //NOTE: The equals method is used on both strings and objects
    public boolean equals(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StudentDTO student = (StudentDTO) object;
        return caoNumber == student.caoNumber &&
                Objects.equals(dateOfBirth, student.dateOfBirth) &&
                Objects.equals(password, student.password) &&
                Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caoNumber, dateOfBirth, password, email);
    }
}
