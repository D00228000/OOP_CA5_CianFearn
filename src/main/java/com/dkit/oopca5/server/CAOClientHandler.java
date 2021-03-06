package com.dkit.oopca5.server;

/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.StudentDTO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CAOClientHandler extends Thread
{
    private Socket dataSocket;
    private Scanner input;
    private PrintWriter output;
    private int number;

    public CAOClientHandler(ThreadGroup group, String name, Socket dataSocket, int number)
    {
        super(group,name);
        try
        {
            this.dataSocket = dataSocket;
            this.number = number;
            input = new Scanner(new InputStreamReader(this.dataSocket.getInputStream()));
            output = new PrintWriter(this.dataSocket.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run()
    {
        //custom run method
        String incomingMessage = "";
        String response = "";
        ICourseDAOInterface courseDAOInterface = new MySqlCourseDAO();
        IStudentDAOInterface studentDAOInterface = new MySqlStudentDAO();
        IStudentCoursesDAOInterface studentCoursesDAOInterface = new MySqlStudentCoursesDAO();

        try
        {
            //The server should run forever hence use a while
            while (true)
            {
                //Take the information from the client
                incomingMessage = input.nextLine();
                System.out.println("Received message: " + incomingMessage);

                String[] messageComponents = incomingMessage.split(CAOService.BREAKING_CHARACTER);

                if (messageComponents[0].equalsIgnoreCase(CAOService.END_SESSION))
                {
                    response = CAOService.SESSION_TERMINATED;
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.REGISTER_COMMAND))
                {
                    if(messageComponents[1].equals(CAOService.INVALID_REGISTRATION))
                    {
                        response = CAOService.INVALID_REGISTRATION;
                    }
                    else
                    {
                        StudentDTO studentToRegister = new StudentDTO(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]);

                        //compare CAO numbers
                        if(studentToRegister.getCaoNumber() == studentDAOInterface.checkForMatchingCAONumbers(Integer.parseInt(messageComponents[1])))
                        {
                            response = CAOService.FAILED_REGISTER;
                        }
                        else
                        {
                            studentDAOInterface.registerStudent(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]);
                            response = CAOService.SUCCESSFUL_REGISTER;
                        }
                    }
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.ATTEMPT_LOGIN))
                {
                    StudentDTO studentToCompareWithDatabase = new StudentDTO(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]);

                    if(studentToCompareWithDatabase.equals(studentDAOInterface.findStudentCAO(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4])))
                    {
                        response = CAOService.SUCCESSFUL_LOGIN;
                    }
                    else
                    {
                        response = CAOService.FAILED_LOGIN;
                    }
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.LOG_OUT))
                {
                    response = CAOService.LOG_OUT;
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_COURSE))
                {
                    if(courseDAOInterface.findCertainCourse(messageComponents[1]) != null)
                    {
                        response = courseDAOInterface.findCertainCourse(messageComponents[1]).toString();
                    }
                    else
                    {
                        response = CAOService.FAILED_DISPLAY_COURSE;
                    }
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_ALL_COURSES))
                {
                    response = courseDAOInterface.findAllCourses().toString();
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_CURRENT_CHOICES))
                {
                    response = studentCoursesDAOInterface.findCertainStudentsChoices(Integer.parseInt(messageComponents[1]));
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.UPDATE_CURRENT_CHOICES))
                {
                    if(courseDAOInterface.findCertainCourse(messageComponents[2]) != null)
                    {
                        studentCoursesDAOInterface.updateCourseChoices(Integer.parseInt(messageComponents[1]),messageComponents[2]);
                        response = CAOService.UPDATE_CHOICES_SUCCESS;
                    }
                    else
                    {
                        response = CAOService.UPDATE_CHOICES_FAILED;
                    }
                }
                else
                {
                    response = CAOService.UNKNOWN;
                }
                output.println(response);
                output.flush();
                //sends information back to the client
            }
        }
        catch (NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
        catch (DAOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                System.out.println("The connection has closed between the server and the client #"+number);
                dataSocket.close();
            }
            catch (IOException e)
            {
                System.out.println("The Client #"+number+" is unable to disconnect "+e.getMessage());
                System.exit(1);//closes the system down
            }
        }
    }
}
