package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.DAOException;
import com.dkit.oopca5.server.IStudentDAOInterface;
import com.dkit.oopca5.server.MySqlStudentDAO;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CAOClient
{
    private static boolean loggedIntoAccount = false;

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
                        initialMenuChoices = InitialMenuChoices.values()[Integer.parseInt(keyboard.nextLine().trim())];
                        System.out.println();

                        switch (initialMenuChoices)
                        {
                            case REGISTER:
                                //registerStudent(keyboard);
                                //@TODO need to register and add to a database
                                message = CAOService.REGISTER_COMMAND+CAOService.BREAKING_CHARACTER+registerStudent(keyboard);
                                output.println(message);
                                output.flush();

                                response = input.nextLine();
                                if(response.equals(CAOService.SUCCESSFUL_REGISTER))
                                {
                                    System.out.println("The student has been registered\n");
                                }
                                else if(response.equals(CAOService.FAILED_REGISTER))
                                {
                                    System.out.println("The student has not been registered\n");
                                }
                                break;
                            case LOGIN:
                                message = CAOService.ATTEMPT_LOGIN+login(keyboard);
                                output.println(message);
                                output.flush();
                                response = input.nextLine();
                                System.out.println(response);
                                if(response.equals(CAOService.SUCCESSFUL_LOGIN))
                                {
                                    loggedIntoAccount = true;
                                }
//                                else if(response.equals(CAOService.FAILED_LOGIN))
//                                {
//                                    System.out.println(Colours.RED+"You have failed to log in with these credentials"+Colours.RESET);
//                                }

                                if(loggedIntoAccount)

                                {
                                    System.out.println("You have logged in");
                                    loggedInMenuSystem(keyboard, output,input,response);
                                }

                                //
                                break;
                            case QUIT:
                                message = CAOService.END_SESSION;
                                output.println(message);
                                output.flush();

                                //Listen for a response
                                response = input.nextLine();
                                if(response.equals(CAOService.SESSION_TERMINATED))
                                {
                                    System.out.println("Session has been terminated");
                                }
                                break;
                            default:
                                System.out.println(Colours.RED+"Selection out of range. Try again\n"+Colours.RESET);
                        }
                        if(response.equals(CAOService.UNKNOWN))
                        {
                            System.out.println("Sorry, invalid command");
                            System.out.println("Please enter a valid option\n");
                        }
                    }
                    catch(NoSuchElementException e)
                    {
                        System.out.println(Colours.RED+"Selection out of range. Try again:"+Colours.RESET+"\n");
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
            System.out.println("Thanks for using the TCP ComboService");
            dataSocket.close();
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
    }

    //@TODO Need to allow someone to log in
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

        System.out.println("Please enter your password (8-16 characters)");
        String password = keyboard.next();
        keyboard.nextLine();

        Pattern passwordPattern = Pattern.compile(RegexChecker.passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        //checks that the info matches the patterns
        if(CAOMatcher.matches() && emailMatcher.matches() && DOBMatcher.matches() && passwordMatcher.matches())
        {
            //LOGIN%%$caoNumber%%$dateOfBirth%%$password
            return CAOService.BREAKING_CHARACTER+/*CAOService.VARIABLE+*/caoNumber+CAOService.BREAKING_CHARACTER+/*CAOService.VARIABLE+*/DOB+CAOService.BREAKING_CHARACTER+/*CAOService.VARIABLE+*/password+/*CAOService.VARIABLE+*/CAOService.BREAKING_CHARACTER+email;
//            boolean studentExists = true; // true by default check id, password, and DOB
//
//            //create an if statement
//            if(studentExists)
//            {
//                loggedIntoAccount = true;
//            }
//            if(loggedIntoAccount)
//            {
//                //System.out.println("You have logged in");
//                //loggedInMenuSystem(keyboard, output,input,response);
//            }
//            else
//            {
//                System.out.println("Incorrect login details. Try again");
//            }
        }
        else
        {
            return Colours.RED+"The information provided was invalid"+Colours.RESET;
        }

    }

    //this allows the the logged in menu to be displayed and used
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
                loggedInMenu = LoggedInMenu.values()[Integer.parseInt(keyboard.nextLine().trim())];
                //keyboard.nextLine();

                switch (loggedInMenu)
                {
                    //TODO fix methods. currently have loop issues or skips over code
                    case LOGOUT:
                        System.out.println("You have logged out of the System\n");
                        loggedIntoAccount = false;
                        message = CAOService.LOG_OUT;
                        output.println(message);
                        output.flush();
                        //loggedInMenu = loggedInMenu.QUIT;
                        loggedInMenu = loggedInMenu.LOGOUT;
                        break;
                    case DISPLAY_COURSE:
                        //displayCertainCourse(output);
                        message = CAOService.DISPLAY_COURSE;
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
                        //@TODO try to fix how this is displayed
                        System.out.println(response);//printing out the responses
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        message = CAOService.DISPLAY_CURRENT_CHOICES;
                        output.println(message);
                        output.flush();
                        //displayCurrentChoices(response);
                        response = input.nextLine();

                        System.out.println(response);
                        break;
                    case UPDATE_CURRENT_CHOICES:
                        //login(keyboard,loggedIntoAccount);
                        message = CAOService.UPDATE_CURRENT_CHOICES;
                        output.println(message);
                        output.flush();
                        //updateCurrentChoices();
                        //@TODO may need to remove this response
                        response = input.nextLine();
                        System.out.println(response);
                        break;
                    default:
                        System.out.println(Colours.RED+"Selection out of range. Im here Try again"+Colours.RESET);
                }
                if(response.equals(CAOService.UNKNOWN))
                {
                    System.out.println("Sorry, invalid command");
                    System.out.println("Please enter a valid option\n");
                }
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println(Colours.RED+"Selection out of range. Try again"+Colours.RESET);
            }
            catch(NoSuchElementException e)
            {
                System.out.println(Colours.RED+"Selection out of range. Try again:"+Colours.RESET);
            }
            catch (NumberFormatException e)
            {
                System.out.println(Colours.RED+"Selection out of range. Try again"+Colours.RESET);
            }
        }
    }

    private static void displayCertainCourse(PrintWriter output)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter a course id here:");
        String courseIDtoFind = keyboard.nextLine();

        String message = CAOService.DISPLAY_COURSE+CAOService.BREAKING_CHARACTER+courseIDtoFind;
        output.println(message);
        output.flush();
    }

    private static String displayCurrentChoices(String response) {
        return null;
    }

    private static void updateCurrentChoices() {
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

    //@TODO Need to allow a student to be registered make this a student and return them
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

        System.out.println("Please enter your password (8-16 characters)");
        String password = keyboard.nextLine();

        Pattern passwordPattern = Pattern.compile(RegexChecker.passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        System.out.println("Please enter your email");
        String email = keyboard.nextLine();

        Pattern emailPattern = Pattern.compile(RegexChecker.emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        //Validating all the information
        //check CAO number
        //@TODO maybe make this a single if statement in the future
        if(CAOMatcher.matches() && DOBMatcher.matches() && passwordMatcher.matches() && emailMatcher.matches())
        {
            //creates the student and call the add method
            //Student studentToRegister = new Student(caoNumber, DOB, password, email);
            //System.out.println("Made it");
            //studentManager.addStudent(studentToRegister);
            //@TODO this student needs to be added to a data base

            //@TODO return the string of info then send it in parent method
            return  CAOService.VARIABLE+caoNumber+CAOService.VARIABLE+DOB+CAOService.VARIABLE+password+CAOService.VARIABLE+email;
        }
        else
        {
            System.out.println(Colours.RED+"Incorrect details for the account"+Colours.RESET);
        }
        return "FAILED";
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
