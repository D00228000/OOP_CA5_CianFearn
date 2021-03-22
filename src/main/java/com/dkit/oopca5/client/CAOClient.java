package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Student;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CAOClient
{
    private static boolean loggedIntoAccount = false;

    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        //Create the client socket
        DatagramSocket clientSocket = null;

        try
        {
            InetAddress serverHost = InetAddress.getByName(CAOService.HOSTNAME);
            clientSocket = new DatagramSocket(CAOService.CLIENT_PORT);

            String message = null;
            boolean sendMessage = true;

            boolean continueRunning = true;
            while(continueRunning)//maybe remove this while loop as its an infinite loop in another infinite loop
            {
                //allow for input and other options
                InitialMenuChoices initialMenChoices = InitialMenuChoices.CONTINUE;
                while (initialMenChoices != InitialMenuChoices.QUIT)
                {
                    //display initial menu
                    displayInitialMenu();
                    initialMenChoices = InitialMenuChoices.values()[Integer.parseInt(keyboard.nextLine().trim())];
                    System.out.println();

                    switch (initialMenChoices)
                    {
                        case REGISTER:
                            registerStudent(keyboard);
                            message = CAOService.REGISTER_COMMAND;
                            break;
                        case LOGIN:
                            login();
                            break;
                        case QUIT:
                            continueRunning = false;
                            sendMessage = false;
                            System.out.println("You have exited the System");
                            break;
                        default:
                            System.out.println(Colours.RED+"Selection out of range. Try again"+Colours.RESET);
                    }




                }




            }
            if(sendMessage)
            {
                //sending the message
                byte buffer[] = message.getBytes();
                DatagramPacket requestedPacket = new DatagramPacket(buffer, buffer.length,serverHost,CAOService.SERVER_PORT);
                clientSocket.send(requestedPacket);
                System.out.println("Message sent");

                //wait to receive a response
                byte[] responseBuffer = new byte[CAOService.MAX_LEN];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, requestedPacket.getLength());

                //Receives a response
                clientSocket.receive(responsePacket);
                String data = new String(responsePacket.getData());
                System.out.println("RESPONSE: "+ data.trim()+".\n");

            }

        }
        catch (UnknownHostException e)
        {
            System.out.println("Problem getting server address "+e.getMessage());
        }
        catch (SocketException e)
        {
            System.out.println("Problem binding clientSocket to port "+e.getMessage());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        finally
        {
            if(clientSocket != null)
            {
                clientSocket.close();
            }
        }

    }

    //@TODO Need to allow someone to log in
    private static void login()
    {
        //a person enters their cao number, date of birth and password
        //@TODO this needs to be checked by comparing the database to the entered information
        //if successful go to another menu




        // UNCOMMENT THIS IF LOGGED IN ::: loggedIntoAccount = true;
    }

    //@TODO Need to allow a student to be registered
    private static void registerStudent(Scanner keyboard)
    {
        //register a student here
        System.out.println("Please enter your CAO number (8 characters long)");
        int caoNumber = keyboard.nextInt();

        String caoNumberAsString = (Integer.toString(caoNumber));
        Pattern CAOPattern = Pattern.compile(RegexChecker.CAORegex);
        Matcher CAOMatcher = CAOPattern.matcher(caoNumberAsString);

        System.out.println("Please enter your date of birth (yyyy-mm-dd");
        String DOB = keyboard.nextLine();

        Pattern DOBPattern = Pattern.compile(RegexChecker.DOBRegex);
        Matcher DOBMatcher = DOBPattern.matcher(DOB);

        System.out.println("Please enter your CAO number");
        String password = keyboard.nextLine();

        Pattern passwordPattern = Pattern.compile(RegexChecker.passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        System.out.println("Please enter your CAO number");
        String email = keyboard.nextLine();

        Pattern emailPattern = Pattern.compile(RegexChecker.emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        //Validating all the information
        //check CAO number
        //@TODO maybe make this a single if statement in the future
        if(CAOMatcher.matches())
        {
            if(DOBMatcher.matches())
            {
                //check the password
                if(passwordMatcher.matches())
                {
                    //check the email
                    if(emailMatcher.matches())
                    {
                        //creates the student and call the add method
                        Student studentToRegister = new Student(caoNumber, DOB, password, email);
                        //studentManager.addStudent(studentToRegister);
                        //@TODO this student needs to be added to a data base
                    }
                    else
                    {
                        System.out.println(Colours.RED+"This is not a valid email\n"+Colours.RESET);
                    }
                }
                else
                {
                    System.out.println(Colours.RED+"This is not a valid password\n"+Colours.RESET);
                }
            }
            else
            {
                System.out.println(Colours.RED+"This is not a valid date of birth\n"+Colours.RESET);
            }
        }
        else
        {
            System.out.println(Colours.RED+"This is not a valid CAO number\n"+Colours.RESET);
        }
    }

    private static void displayInitialMenu()
    {
        //This menu is used to print out the initial menu
        System.out.println("0: QUIT");
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.print("Please enter one of the menu options:");
    }
}
