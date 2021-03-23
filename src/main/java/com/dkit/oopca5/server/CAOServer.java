package com.dkit.oopca5.server;

/* The server package should contain all code to run the server. The server uses TCP sockets and thread per client.
 The server should connect to a MySql database to register clients, allow them to login and choose courses
 The server should listen for connections and once a connection is accepted it should spawn a new CAOClientHandler thread to deal with that connection. The server then returns to listening
 */

import com.dkit.oopca5.core.CAOService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class CAOServer
{
    public static void main(String[] args)
    {
        boolean continueRunning = true;
        System.out.println("This is the server");

        DatagramSocket serverSocket = null;
        try
        {
            //create a socket to send and receive
            serverSocket = new DatagramSocket(CAOService.SERVER_PORT);

            //The server should run forever hence use a while
            while(continueRunning)
            {
                //create an array to store incoming messages
                byte[] incomingMessage = new byte[CAOService.MAX_LEN];
                DatagramPacket incomingPacket = new DatagramPacket(incomingMessage, incomingMessage.length);

                //wait for a message
                //BLOCKING
                serverSocket.receive(incomingPacket);
                System.out.println("Listening on port: "+CAOService.SERVER_PORT);

                //recreate the message received
                String data = new String(incomingPacket.getData());
                data = data.trim();
                String[] messageComponents = data.split(CAOService.BREAKING_CHARACTER);

                //create a response
                if(messageComponents[0].equalsIgnoreCase(CAOService.REGISTER_COMMAND))
                {
                    //example response
                    //response = data.replace("echo"+ComboServiceDetails.breakCharacters,"");

                    //create proper responses with database connection response
                }
                else if(messageComponents[0].equalsIgnoreCase("LOGIN"))
                {

                }
                else if(messageComponents[0].equalsIgnoreCase("LOGOUT"))
                {

                }
                else if(messageComponents[0].equalsIgnoreCase("DISPLAY COURSE"))
                {

                }
                else if(messageComponents[0].equalsIgnoreCase("DISPLAY CURRENT"))
                {

                }
                else if(messageComponents[0].equalsIgnoreCase("DISPLAY_ALL"))
                {

                }
                else if(messageComponents[0].equalsIgnoreCase("UPDATE CURRENT"))
                {

                }
            }
        }
        catch(SocketException e)
        {
            System.out.println("There was an error creating the server socket "+ e.getMessage());
        }
        catch(IOException ioe)
        {
            System.out.println("There was an IO error on the server "+ioe.getMessage());
        }
        finally
        {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        }
    }
}
