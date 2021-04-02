package com.dkit.oopca5.server;

import com.dkit.oopca5.core.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//This DAO will focus on the sql student table
//Maybe add a student interface //TODO add email for student info and in the database
public class MySqlStudentDAO extends MySqlDAO implements IStudentDAOInterface
{
    @Override
    public Student findStudentCAO(int caoNumber, String DOB, String password) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student returnedStudent = null;

        try
        {
            connection = this.getConnection();
            //note an issue with this sql statement
            String query = "select cao_number, dob, password from student where cao_number = "+caoNumber+" and dob = "+DOB+" and password = "+password+";";
            //              select cao_number, dob, password from student where cao_number = 12345678 and dob = "2001-06-04" and password = "testpassword";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int CAOID = rs.getInt("cao_number");
                String DateOfBirth = rs.getString("dob");
                String dbPassword = rs.getString("password");

                //TODO need to edit
                String defaultEmail = "defaultEmail@gmail.com";
                returnedStudent = new Student(CAOID,DateOfBirth,dbPassword,defaultEmail);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("findStudentCAO "+e.getMessage());
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
                throw new DAOException("findStudentCAO final "+e.getMessage());
            }

        }

        return returnedStudent;
    }
    //obtain a student
}
