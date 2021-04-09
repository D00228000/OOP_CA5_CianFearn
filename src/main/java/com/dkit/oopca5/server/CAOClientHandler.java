package com.dkit.oopca5.server;

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.Student;

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
    public void run() {
        //custom run method
        String incomingMessage = "";
        String response = "";
        ICourseDAOInterface courseDAOInterface = new MySqlCourseDAO();
        IStudentDAOInterface studentDAOInterface = new MySqlStudentDAO();

        try
        {
            //The server should run forever hence use a while
            while (true)
            {
                //Take the information from the client
                incomingMessage = input.nextLine();
                System.out.println("Received message: " + incomingMessage);

                String[] messageComponents = incomingMessage.split(CAOService.BREAKING_CHARACTER);

                //TODO add the functionality methods here the interfaces
                if (messageComponents[0].equalsIgnoreCase(CAOService.END_SESSION))
                {
                    response = "END SESSION";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.REGISTER_COMMAND))
                {

                    //TODO add more to this

                    response = CAOService.SUCCESSFUL_REGISTER;

                    //create a temporary student object and compare it to what the database returns

                    //courseDAOInterface.findAllCourses().toString();
                    //need to use an interface to find the student then send back the correct response
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.ATTEMPT_LOGIN))
                {

                    //getLogin();
                    //need to remove the $
                    Student studentToCompareWithDatabase = new Student(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]);


                    if(studentToCompareWithDatabase != studentDAOInterface.findStudentCAO(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]))
                    {
                        System.out.println("THIS WORKS INCORRECTLY");

                        System.out.println("-----------------------------------------");
                        System.out.println(studentToCompareWithDatabase.toString());

                        System.out.println("-----------------------------------------");
                        System.out.println(studentDAOInterface.findStudentCAO(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]));
                        System.out.println("-----------------------------------------");
                        response = CAOService.SUCCESSFUL_LOGIN;
                        //response = CAOService.FAILED_LOGIN;
                    }
//                    else if(studentToCompareWithDatabase != studentDAOInterface.findStudentCAO(Integer.parseInt(messageComponents[1]),messageComponents[2],messageComponents[3] ,messageComponents[4]))
//                    {
//                        response = CAOService.FAILED_LOGIN;
//                    }
                    //response = CAOService.SUCCESSFUL_LOGIN;
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.LOG_OUT))
                {
                    response = "LOGGED OUT";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_COURSE))
                {
                    //String enteredCourseID = messageComponents[1];
                    //response = courseDAOInterface.findCertainCourse(messageComponents[1]).toString();
                    response = "DISPLAY COURSE";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_ALL_COURSES))
                {
                    //@TODO need to fix this
                    //display the courses contact the database
                    response = courseDAOInterface.findAllCourses().toString();
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_CURRENT_CHOICES))
                {
                    response = "DISPLAY CURRENT CHOICES";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.UPDATE_CURRENT_CHOICES))
                {
                    response = "UPDATE CURRENT CHOICES";
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
