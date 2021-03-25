package com.dkit.oopca5.server;

/* The server package should contain all code to run the server. The server uses TCP sockets and thread per client.
 The server should connect to a MySql database to register clients, allow them to login and choose courses
 The server should listen for connections and once a connection is accepted it should spawn a new CAOClientHandler thread to deal with that connection. The server then returns to listening
 */

import com.dkit.oopca5.core.CAOService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CAOServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket listeningSocket = new ServerSocket(CAOService.LISTENING_ON_PORT);
            Socket dataSocket = new Socket();

            boolean continueRunning = true;
            while (continueRunning)
            {
                System.out.println("Sever is listening on port: " + CAOService.LISTENING_ON_PORT);
                dataSocket = listeningSocket.accept();

                //Creating the input and output streams
                OutputStream outputStream = dataSocket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(outputStream));
                InputStream inputStream = dataSocket.getInputStream();
                Scanner input = new Scanner(new InputStreamReader(inputStream));
                //Keyboard and message declarations
                String incomingMessage = "";
                String response;

                //The server should run forever hence use a while
                while (true)
                {
                    response = null;
                    //Take the information from the client
                    incomingMessage = input.nextLine();
                    System.out.println("Received message: " + incomingMessage);

                    String[] messageComponents = incomingMessage.split(CAOService.BREAKING_CHARACTER);

                    //create a response
                    if (messageComponents[0].equalsIgnoreCase(CAOService.REGISTER_COMMAND))
                    {
                        //example response
                        //response = data.replace("echo"+ComboServiceDetails.breakCharacters,"");
                            //create proper responses with database connection response
                    }
                    else if (messageComponents[0].equalsIgnoreCase("LOGIN"))
                    {

                    }
                    else if (messageComponents[0].equalsIgnoreCase("LOGOUT"))
                    {

                    }
                    else if (messageComponents[0].equalsIgnoreCase("DISPLAY COURSE"))
                    {

                    }
                    else if (messageComponents[0].equalsIgnoreCase("DISPLAY CURRENT"))
                    {

                    }
                    else if (messageComponents[0].equalsIgnoreCase("DISPLAY_ALL"))
                    {

                    }
                    else if (messageComponents[0].equalsIgnoreCase("UPDATE CURRENT"))
                    {

                    }
                    output.println(response);
                    output.flush();
                }
            }
            dataSocket.close();
            listeningSocket.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e)
        {
            System.out.println("A client has disconnected from the server");
            //dataSocket.close();
            //listeningSocket.close();
            //System.exit(1);
        }

    }
}

