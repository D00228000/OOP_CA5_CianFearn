package com.dkit.oopca5.core;

/* The CAOService class has constants to define all of the messages that are sent between the Client and Server
 */

public class CAOService
{
    public static final int CLIENT_PORT = 50025;
    public static final String HOSTNAME = "localhost";
    public static final int  SERVER_PORT = 50026;

    public static final String BREAKING_CHARACTER = "%%";
    public static final int MAX_LEN = 200;

    public static final String REGISTER_COMMAND = "REGISTER";
    public static final String SUCCESSFUL_REGISTER = "REGISTERED";
    public static final String FAILED_REGISTER = "REG FAILED";
}
