package com.dkit.oopca5.server;

import com.dkit.oopca5.core.StudentDTO;

public interface IStudentDAOInterface
{
    public StudentDTO findStudentCAO(int caoNumber, String DOB, String password, String email) throws DAOException;
    public int checkForMatchingCAONumbers(int caoNumber) throws DAOException;
    public void registerStudent(int caoNumber, String DOB, String password, String email) throws DAOException;
}
