package com.dkit.oopca5.server;

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.oopca5.core.CAOService;
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

        try
        {
            //The server should run forever hence use a while
            while (true)
            {
                response = null;
                //Take the information from the client
                incomingMessage = input.nextLine();
                System.out.println("Received message: " + incomingMessage);

                String[] messageComponents = incomingMessage.split(CAOService.BREAKING_CHARACTER);

                if (messageComponents[0].equalsIgnoreCase(CAOService.END_SESSION))
                {
                    response = "Quit";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.REGISTER_COMMAND))
                {
                    response = CAOService.SUCCESSFUL_REGISTER;
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.ATTEMPT_LOGIN))
                {
                    //getLogin();
                    response = CAOService.SUCCESSFUL_LOGIN;
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.LOG_OUT))
                {
                    response = "LOGGED OUT";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_COURSE))
                {
                    response = "DISPLAY COURSE";
                }
                else if (messageComponents[0].equalsIgnoreCase(CAOService.DISPLAY_ALL_COURSES))
                {
                    response = "DISPLAY ALL COURSES";
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
                //Gives out response
                output.println(response);
                output.flush();
            }
        }
        catch (NoSuchElementException e)
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
