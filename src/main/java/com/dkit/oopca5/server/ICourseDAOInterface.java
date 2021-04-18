package com.dkit.oopca5.server;
/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */
import com.dkit.oopca5.core.CourseDTO;
import java.util.ArrayList;

public interface ICourseDAOInterface
{
    public ArrayList<CourseDTO> findAllCourses() throws DAOException;
    public CourseDTO findCertainCourse(String courseID) throws  DAOException;
}
