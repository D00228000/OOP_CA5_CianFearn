package com.dkit.oopca5.server;

import com.dkit.oopca5.core.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//This DAO will focus on the sql course table
public class MySqlCourseDAO extends MySqlDAO implements ICourseDAOInterface
{
    @Override
    public ArrayList<Course> findAllCourses() throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Course returnedCourse = null;
        ArrayList<Course> findAllCoursesArrayList = new ArrayList<>();
        try
        {
            connection = this.getConnection();
            String query = "select * from course";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                //course_id | level | title                   | institution
                String courseID = rs.getString("course_id");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                returnedCourse = new Course(courseID,level,title,institution);
                findAllCoursesArrayList.add(returnedCourse);

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("findAllCourses "+e.getMessage());
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
                throw new DAOException("findAllCourses final "+e.getMessage());
            }
        }
        return findAllCoursesArrayList;
    }

    @Override
    public Course findCertainCourse(String courseID) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Course returnedCourse = null;
        try
        {
            connection = this.getConnection();
            String query = "select * from course where course_id = "+courseID;
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                //course_id | level | title                   | institution
                String dbCourseID = rs.getString("course_id");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                returnedCourse = new Course(dbCourseID,level,title,institution);;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("findCertainCourse "+e.getMessage());
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
                throw new DAOException("findCertainCourse final "+e.getMessage());
            }
        }
        return returnedCourse;
    }
}
