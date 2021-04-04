package com.dkit.oopca5.server;

import com.dkit.oopca5.core.Course;

import java.util.ArrayList;

public interface ICourseDAOInterface
{
    public ArrayList<Course> findAllCourses() throws DAOException;
    public Course findCertainCourse(String courseID) throws  DAOException;
}
