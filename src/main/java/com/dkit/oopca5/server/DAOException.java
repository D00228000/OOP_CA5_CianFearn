package com.dkit.oopca5.server;

import java.sql.SQLDataException;

public class DAOException extends SQLDataException
{
    public DAOException()
    {

    }

    public DAOException(String aMessage)
    {
        super(aMessage);
    }

}
