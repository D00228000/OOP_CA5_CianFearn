package com.dkit.oopca5.core;

/* The CAOService class has constants to define all of the messages that are sent between the Client and Server
 */

public class CAOService
{
    //NOTE: UPD uses separate ports
    //NOTE TCP uses a single port for communication

    public static final String HOSTNAME = "localhost";
    public static final int LISTENING_ON_PORT = 50025;

    public static final String BREAKING_CHARACTER = "%%";

    public static final String REGISTER_COMMAND = "REGISTER";
    public static final String SUCCESSFUL_REGISTER = "REGISTERED";
    public static final String INVALID_REGISTRATION ="INVALID REGISTRATION";
    public static final String FAILED_REGISTER = "REG FAILED";

    public static final String ATTEMPT_LOGIN = "ATTEMPT LOGIN";
    public static final String SUCCESSFUL_LOGIN = "LOGIN";
    public static final String FAILED_LOGIN = "FAILED LOGIN";
    public static final String LOG_OUT = "LOGGED OUT";

    public static final String DISPLAY_COURSE = "DISPLAY COURSE";
    public static final String FAILED_DISPLAY_COURSE = "FAILED TO DISPLAY THIS COURSE";
    public static final String DISPLAY_ALL_COURSES = "DISPLAY ALL COURSES";

    public static final String DISPLAY_CURRENT_CHOICES = "DISPLAY CURRENT CHOICES";

    public static final String UPDATE_CURRENT_CHOICES = "UPDATE CURRENT CHOICES";
    public static final String UPDATE_CHOICES_FAILED = "FAILED TO UPDATE CHOICES";
    public static final String UPDATE_CHOICES_SUCCESS = "UPDATED CHOICES";

    public static final String UNKNOWN = "UNKNOWN COMMAND";

    public static final String END_SESSION = "QUIT";
    public static final String SESSION_TERMINATED = "GOODBYE";
}
