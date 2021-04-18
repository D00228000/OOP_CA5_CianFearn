package com.dkit.oopca5.core.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.dkit.oopca5.core.CourseDTO;
import com.dkit.oopca5.core.StudentDTO;
import com.dkit.oopca5.server.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DatabaseTests
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void findCertainStudentsChoicesTest()
    {
        try
        {
            String testCourseID = "DK823";
            int caoNumber = 12349999;
            //String query = "select cao_number, dob, password from student where cao_number = "+caoNumber+" and dob = "+DOB+" and password = "+password+";";
            IStudentCoursesDAOInterface iStudentCourses = new MySqlStudentCoursesDAO();

            if(testCourseID.equals(iStudentCourses.findCertainStudentsChoices(caoNumber)))
            {
                System.out.println("findCertainStudentsChoicesTest() passed");
                assertTrue(true);
            }
            else
            {
                System.out.println("findCertainStudentsChoicesTest() failed");
                fail();
            }
        }
        catch (DAOException throwables)
        {
            throwables.printStackTrace();
        }
    }

    @Test
    public void findCertainCourseTest()
    {
        try
        {
            CourseDTO testCourseID = new CourseDTO("DK823",8,"Mathematics and Data", "Dundalk Institute of Technology");
            ICourseDAOInterface iCourseDAO = new MySqlCourseDAO();

            if(testCourseID == iCourseDAO.findCertainCourse("DK823"))
            {
                System.out.println("findCertainCourseTest() passed");
                assertTrue(true);
            }
        }
        catch (DAOException throwables)
        {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testFindStudentCAO()
    {
        int caoNumber = 12349999;
        String dob = "2002-01-01";
        String password = "C00LP1zza";
        String email = "JayBeklis@gmail.com";
        //String query = "select cao_number, dob, password from student where cao_number = "+caoNumber+" and dob = "+DOB+" and password = "+password+";";
        IStudentDAOInterface iStudent = new MySqlStudentDAO();

        try
        {
            StudentDTO testStudent = new StudentDTO(caoNumber,dob,password,email);

            if(testStudent == iStudent.findStudentCAO(caoNumber,dob,password,email))
            {
                System.out.println("testFindStudentCAO() passed");
                assertTrue(true);
            }
        }
        catch (DAOException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
