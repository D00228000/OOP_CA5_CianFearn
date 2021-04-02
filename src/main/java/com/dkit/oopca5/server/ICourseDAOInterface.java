package com.dkit.oopca5.server;

import com.dkit.oopca5.core.Course;

public interface ICourseDAOInterface
{
    public Course findAllCourses() throws DAOException;
}
