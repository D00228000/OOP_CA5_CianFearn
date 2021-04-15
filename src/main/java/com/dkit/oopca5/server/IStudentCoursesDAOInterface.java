package com.dkit.oopca5.server;

public interface IStudentCoursesDAOInterface
{
    public String findCertainStudentsChoices(int caoNumber) throws DAOException;

    void updateCourseChoices(int caoNumber, String courseToAdd) throws DAOException;
}
