package com.dkit.oopca5.server;
/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */
public interface IStudentCoursesDAOInterface
{
    public String findCertainStudentsChoices(int caoNumber) throws DAOException;
    void updateCourseChoices(int caoNumber, String courseToAdd) throws DAOException;
}
