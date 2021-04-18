package com.dkit.oopca5.server;
/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//This DAO will focus on the sql student_course table
public class MySqlStudentCoursesDAO extends MySqlDAO implements IStudentCoursesDAOInterface
{
    //TODO need to create a variable that remembers who is logged in the client class
    public String findCertainStudentsChoices(int caoNumber) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> choiceCourses = new ArrayList<>();
        String dbCourseID = "";

        try
        {
            connection = this.getConnection();
            String query = "select course_id from student_courses where cao_number = "+caoNumber+";";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                dbCourseID = rs.getString("course_id");
                choiceCourses.add(dbCourseID);
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
        return choiceCourses.toString();
    }

    @Override
    public void updateCourseChoices(int caoNumber, String courseToAdd) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            connection = this.getConnection();
            String query = "insert into student_courses (cao_number,course_id) values ("+caoNumber+",\""+courseToAdd+"\");";
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DAOException("updateCourseChoices " + e.getMessage());
        }
        finally
        {
            try
            {
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
    }
}
