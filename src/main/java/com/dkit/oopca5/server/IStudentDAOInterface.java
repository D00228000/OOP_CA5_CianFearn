package com.dkit.oopca5.server;

import com.dkit.oopca5.core.Student;

public interface IStudentDAOInterface
{
    public Student findStudentCAO(int caoNumber, String DOB, String password, String email) throws DAOException;
    public int checkForMatchingCAONumbers(int caoNumber) throws DAOException;
    public void registerStudent(int caoNumber, String DOB, String password, String email) throws DAOException;
}
