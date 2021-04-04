package com.dkit.oopca5.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//This DAO will focus on the sql student_course table
public class MySqlStudentCoursesDAO extends MySqlDAO implements IStudentCoursesDAOInterface
{
    //TODO need to create a variable that remembers who is logged in the client class
    public String findCertainStudentsChoices(int caoNumber) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String dbCourseID = "";

        try
        {
            connection = this.getConnection();
            String query = "select course_id from student_courses where cao_number = "+caoNumber+";";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            System.out.println("Your courses are the following");
            while (rs.next())
            {
                dbCourseID = rs.getString("course_id");
                System.out.println(dbCourseID);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("findCertainStudentsChoices " + e.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("findCertainStudentsChoices final "+e.getMessage());
            }
        }
        return dbCourseID;
    }
}
