package com.dkit.oopca5.client;
/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */

/* The client package should contain all code and classes needed to run the Client
   The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CAOClient
{
    private static int accountLoggedInto = 0;
    public static void main(String[] args)
    {
        try
        {
            //Creating a TCP socket
            Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.LISTENING_ON_PORT);
            //Creating the input and output streams
            OutputStream outputStream = dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(outputStream));
            InputStream inputStream = dataSocket.getInputStream();
            Scanner input = new Scanner(new InputStreamReader(inputStream));
            //Keyboard and message declarations
            Scanner keyboard = new Scanner(System.in);
            String message = "";

            while(!message.equals(CAOService.END_SESSION))//maybe remove this while loop as its an infinite loop in another infinite loop
            {
                //allow for input and other options
                InitialMenuChoices initialMenuChoices = InitialMenuChoices.CONTINUE;
                while (initialMenuChoices != InitialMenuChoices.QUIT)//change while to an if because already in a while loop
                {
                    //display initial menu
                    displayInitialMenu();
                    String response = "";

                    try
                    {
                        initialMenuChoices = InitialMenuChoices.values()[Integer.parseInt(keyboard.next().trim())];
                        keyboard.nextLine();

                        switch (initialMenuChoices)
                        {
                            case REGISTER:
                                message = CAOService.REGISTER_COMMAND+CAOService.BREAKING_CHARACTER+registerStudent(keyboard);
                                output.println(message);
                                output.flush();

                                response = input.nextLine();
                                System.out.println(response);
                                break;
                            case LOGIN:
                                message = CAOService.ATTEMPT_LOGIN+login(keyboard);
                                output.println(message);
                                output.flush();
                                response = input.nextLine();
                                System.out.println(response);
                                if(response.equals(CAOService.SUCCESSFUL_LOGIN))
                                {
                                    loggedInMenuSystem(keyboard, output,input,response);
                                }
                                else if(response.equals(CAOService.FAILED_LOGIN))
                                {
                                    System.out.println(Colours.RED+"You have failed to log in with these credentials"+Colours.RESET);
                                    accountLoggedInto = 0;
                                }
                                break;
                            case QUIT:
                                message = CAOService.END_SESSION;
                                output.println(message);
                                output.flush();
                                response = input.nextLine();
                                if(response.equals(CAOService.SESSION_TERMINATED))
                                {
                                    System.out.println(Colours.BLUE+"Session has been terminated"+Colours.RESET);
                                }
                                break;
                            default:
                                System.out.println(Colours.RED+"Selection out of range. Try again\n"+Colours.RESET);
                        }
                        if(response.equals(CAOService.UNKNOWN))
                        {
                            System.out.println(Colours.RED+"Sorry, invalid command"+Colours.RESET);
                            System.out.println("Please enter a valid option\n");
                        }
                    }
                    catch(NoSuchElementException e)
                    {
                        System.out.println(Colours.RED+"Selection out of range. Try again:\n"+Colours.RESET);
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println(Colours.RED+"Selection out of range. Try again\n"+Colours.RESET);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println(Colours.RED+"Selection out of range. Try again\n"+Colours.RESET);
                    }
                }
            }
            System.out.println(Colours.BLUE+"Thanks for using this CAOService"+Colours.RESET);
            dataSocket.close();
        }
        catch (UnknownHostException e)
        {
            System.out.println(Colours.RED+"Problem getting server address "+e.getMessage()+Colours.RESET);
        }
        catch (SocketException e)
        {
            System.out.println(Colours.RED+"Problem binding clientSocket to port "+e.getMessage()+Colours.RESET);
        }
        catch (IOException e)
        {
            System.out.println(Colours.RED+"An ioException has occurred "+e.getMessage()+Colours.RESET);
        }
    }

    private static String login(Scanner keyboard)
    {
        //a person enters their cao number, date of birth and password
        System.out.println("Please enter your CAO number (8 characters long)");
        int caoNumber = keyboard.nextInt();

        String caoNumberAsString = (Integer.toString(caoNumber));
        Pattern CAOPattern = Pattern.compile(RegexChecker.CAORegex);
        Matcher CAOMatcher = CAOPattern.matcher(caoNumberAsString);

        System.out.println("Please enter your date of birth (yyyy-mm-dd)");
        String DOB = keyboard.next();
        keyboard.nextLine();

        Pattern DOBPattern = Pattern.compile(RegexChecker.DOBRegex);
        Matcher DOBMatcher = DOBPattern.matcher(DOB);

        System.out.println("Please enter your email");
        String email = keyboard.next();
        keyboard.nextLine();

        Pattern emailPattern = Pattern.compile(RegexChecker.emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        System.out.println("Please enter your password (8 characters minimum)");
        String password = keyboard.next();
        keyboard.nextLine();

        Pattern passwordPattern = Pattern.compile(RegexChecker.passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        //checks that the info matches the patterns
        if(CAOMatcher.matches() && emailMatcher.matches() && DOBMatcher.matches() && passwordMatcher.matches())
        {
            //save the information of who is currently logged in
            accountLoggedInto = caoNumber;
            //LOGIN%%$caoNumber%%$dateOfBirth%%$password
            return CAOService.BREAKING_CHARACTER+caoNumber+CAOService.BREAKING_CHARACTER+DOB+CAOService.BREAKING_CHARACTER+password+CAOService.BREAKING_CHARACTER+email;
        }
        else
        {
            return CAOService.BREAKING_CHARACTER+CAOService.INVALID_REGISTRATION;
        }
    }

    private static void loggedInMenuSystem(Scanner keyboard, PrintWriter output, Scanner input, String response)
    {
        //allow for input and other options
        LoggedInMenu loggedInMenu = LoggedInMenu.CONTINUE;
        while (loggedInMenu != loggedInMenu.LOGOUT)
        {
            //display initial menu
            displayTheLoggedInMenu();
            String message = "";

            try
            {
                loggedInMenu = LoggedInMenu.values()[Integer.parseInt(keyboard.next().trim())];
                keyboard.nextLine();

                switch (loggedInMenu)
                {
                    case LOGOUT:
                        message = CAOService.LOG_OUT;
                        output.println(message);
                        output.flush();
                        response = input.nextLine();
                        System.out.println(response+"\n");
                        loggedInMenu = loggedInMenu.LOGOUT;
                        break;
                    case DISPLAY_COURSE:
                        message = CAOService.DISPLAY_COURSE+CAOService.BREAKING_CHARACTER+displayCertainCourse();
                        output.println(message);
                        output.flush();
                        response = input.nextLine();
                        System.out.println(response);
                        break;
                    case DISPLAY_ALL_COURSES:
                        message = CAOService.DISPLAY_ALL_COURSES;
                        output.println(message);
                        output.flush();
                        response = input.nextLine();
                        System.out.println(response);
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        message = CAOService.DISPLAY_CURRENT_CHOICES+CAOService.BREAKING_CHARACTER+accountLoggedInto;
                        output.println(message);
                        output.flush();
                        response = input.nextLine();
                        System.out.println(response);
                        break;
                    case UPDATE_CURRENT_CHOICES:
                        message = CAOService.UPDATE_CURRENT_CHOICES+CAOService.BREAKING_CHARACTER+accountLoggedInto+CAOService.BREAKING_CHARACTER+updateCurrentChoices();
                        output.println(message);
                        output.flush();
                        response = input.nextLine();
                        System.out.println(response);
                        break;
                    default:
                        System.out.println(Colours.RED+"Selection out of range. Im here Try again"+Colours.RESET);
                }
                if(response.equals(CAOService.UNKNOWN))
                {
                    System.out.println(Colours.RED+"Sorry, invalid command"+Colours.RESET);
                    System.out.println("Please enter a valid option\n");
                }
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println(Colours.RED+"Selection out of range. Try again\n"+Colours.RESET);
            }
            catch(NoSuchElementException e)
            {
                System.out.println(Colours.RED+"Selection out of range. Try again:\n"+Colours.RESET);
            }
            catch (NumberFormatException e)
            {
                System.out.println(Colours.RED+"Selection out of range. Try again\n"+Colours.RESET);
            }
        }
    }

    private static String displayCertainCourse()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter a course id here:");
        String courseIDtoFind = keyboard.next();
        keyboard.nextLine();
        return courseIDtoFind;
    }

    private static String updateCurrentChoices()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter the course id you would like to add to your choice list here:");
        String courseIDToAdd = keyboard.next();
        keyboard.nextLine();
        return courseIDToAdd;
    }

    private static void displayTheLoggedInMenu()
    {
        System.out.println("\n0: Logout");
        System.out.println("1: Display a course");
        System.out.println("2: Display all courses");
        System.out.println("3: Display current choices");
        System.out.println("4: Update current choices");
        System.out.print("Please enter your option here:");
    }

    private static String registerStudent(Scanner keyboard)
    {
        //register a student here 
        System.out.println("Please enter your CAO number (8 characters long)");
        int caoNumber = keyboard.nextInt();

        String caoNumberAsString = (Integer.toString(caoNumber));
        Pattern CAOPattern = Pattern.compile(RegexChecker.CAORegex);
        Matcher CAOMatcher = CAOPattern.matcher(caoNumberAsString);

        System.out.println("Please enter your date of birth (yyyy-mm-dd)");
        String DOB = keyboard.next();
        keyboard.nextLine();

        Pattern DOBPattern = Pattern.compile(RegexChecker.DOBRegex);
        Matcher DOBMatcher = DOBPattern.matcher(DOB);

        System.out.println("Please enter your password (8 characters minimum)");
        String password = keyboard.next();
        keyboard.nextLine();

        Pattern passwordPattern = Pattern.compile(RegexChecker.passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        System.out.println("Please enter your email");
        String email = keyboard.next();
        keyboard.nextLine();

        Pattern emailPattern = Pattern.compile(RegexChecker.emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        if(CAOMatcher.matches() && DOBMatcher.matches() && passwordMatcher.matches() && emailMatcher.matches())
        {
            return  caoNumber+CAOService.BREAKING_CHARACTER+DOB+CAOService.BREAKING_CHARACTER+password+CAOService.BREAKING_CHARACTER+email;
        }
        else
        {
            return CAOService.INVALID_REGISTRATION;
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
