package com.dkit.oopca5.server;

/* The server package should contain all code to run the server. The server uses TCP sockets and thread per client.
 The server should connect to a MySql database to register clients, allow them to login and choose courses
 The server should listen for connections and once a connection is accepted it should spawn a new CAOClientHandler thread to deal with that connection. The server then returns to listening
 */

import com.dkit.oopca5.core.CAOService;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CAOServer
{
    public static void main(String[] args)
    {
        try
        {
            //creating a tcp socket
            ServerSocket listeningSocket = new ServerSocket(CAOService.LISTENING_ON_PORT);
            //Socket dataSocket = new Socket();

            //creating a threadGroup
            ThreadGroup clientThreadGroup = new ThreadGroup("Client thread group");

            //set the thread priority so more clients are accepted to the system
            clientThreadGroup.setMaxPriority(Thread.currentThread().getPriority()-1);

            //server information below
            boolean continueRunning = true;
            int threadClientCount = 0;
            while (continueRunning)
            {
                System.out.println("Sever is listening on port: " + CAOService.LISTENING_ON_PORT);
                Socket dataSocket = listeningSocket.accept();

                threadClientCount++;
                System.out.println("The server has accepted "+threadClientCount+" clients to the server system");

                //creating the thread with its group and other needed items
                CAOClientHandler newClient = new CAOClientHandler(clientThreadGroup,dataSocket.getInetAddress()+"",dataSocket,threadClientCount);
                newClient.start();
            }
            listeningSocket.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

