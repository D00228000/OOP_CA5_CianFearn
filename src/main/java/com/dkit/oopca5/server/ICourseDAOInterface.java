package com.dkit.oopca5.server;

import com.dkit.oopca5.core.CourseDTO;

import java.util.ArrayList;

public interface ICourseDAOInterface
{
    public ArrayList<CourseDTO> findAllCourses() throws DAOException;
    public CourseDTO findCertainCourse(String courseID) throws  DAOException;
}
