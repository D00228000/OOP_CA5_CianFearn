package com.dkit.oopca5.server;
/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */
import java.sql.SQLDataException;

public class DAOException extends SQLDataException
{
    public DAOException() {}

    public DAOException(String aMessage)
    {
        super(aMessage);
    }
}
