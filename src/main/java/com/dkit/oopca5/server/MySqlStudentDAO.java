package com.dkit.oopca5.server;
/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */
import com.dkit.oopca5.core.StudentDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//This DAO will focus on the sql student table
public class MySqlStudentDAO extends MySqlDAO implements IStudentDAOInterface
{
    @Override
    public StudentDTO findStudentCAO(int caoNumber, String DOB, String password, String email) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StudentDTO returnedStudent = null;

        try
        {
            connection = this.getConnection();
            String query = "select cao_number, dob, password, email from student where cao_number = "+caoNumber+" and dob = \""+DOB+"\" and password = \""+password+"\" and email = \""+email+"\";";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int CAOID = rs.getInt("cao_number");
                String DateOfBirth = rs.getString("dob");
                String dbPassword = rs.getString("password");
                String dbEmail = rs.getString("email");
                returnedStudent = new StudentDTO(CAOID,DateOfBirth,dbPassword,dbEmail);
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

    @Override
    public int checkForMatchingCAONumbers(int caoNumber) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int dbCAONumber = 0;

        try
        {
            connection = this.getConnection();
            //insert into student(cao_number,dob,password,email) values(12345688,"2001-06-04","securepassword","cian@gmail.com");
            String query ="select cao_number from student where cao_number = \""+caoNumber+"\";";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                dbCAONumber = rs.getInt("cao_number");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("checkForMatchingCAONumbers "+e.getMessage());
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
                throw new DAOException("checkForMatchingCAONumbers final "+e.getMessage());
            }
        }
        return dbCAONumber;
    }

    @Override
    public void registerStudent(int caoNumber, String DOB, String password, String email) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            connection = this.getConnection();
            //insert into student(cao_number,dob,password,email) values(12345688,"2001-06-04","securepassword","cian@gmail.com");
            String query ="insert into student(cao_number,dob,password,email) values("+caoNumber+",\""+DOB+"\",\""+password+"\",\""+email+"\");";
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DAOException("registerStudent "+e.getMessage());
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
                throw new DAOException("registerStudent final "+e.getMessage());
            }
        }
    }
}
