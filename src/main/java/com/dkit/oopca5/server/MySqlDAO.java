package com.dkit.oopca5.server;

/*
All of the database functionality should be here. You will need a DAO for each table that you are interacting with in the database
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This Data Access Object will focus on getting a connection
public class MySqlDAO
{
    public Connection getConnection() throws DAOException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop_ca5_cian_fearn";
        //String url = "jdbc:mysql://localhost:3306/gd2_user_database_2021";

        String username = "root";
        String password = "";

        Connection connection = null;

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Database Class not found "+ e.getMessage());
            System.exit(1);
        }
        catch (SQLException e)
        {
            System.out.println("Database Connection failed " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Connected successfully");
        return connection;
    }

    public void freeConnection(Connection connection) throws DAOException
    {
        try
        {
            if(connection != null)
            {
                connection.close();
                connection = null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to free the connection "+ e.getMessage());
            System.exit(1);
        }
    }
}
