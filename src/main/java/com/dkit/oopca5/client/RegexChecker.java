package com.dkit.oopca5.client;

/* This class should contain static methods to verify input in the application
 */

public class RegexChecker
{
    public static final String CAORegex = "\\d{8}";
    public static final String DOBRegex = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";
    public static final String passwordRegex = "^(.+){8,16}$";
    //https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
    public static final String emailRegex = "^(.+)@(.+)$";
}
