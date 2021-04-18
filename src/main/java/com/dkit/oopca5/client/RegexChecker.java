package com.dkit.oopca5.client;

/* This class should contain static methods to verify input in the application
 */

public class RegexChecker
{
    public static final String CAORegex = "\\d{8}";
    public static final String DOBRegex = "^[1920-9999]{4}\\-[0-3]{1}[1-9]{1}\\-[0-1]{1}[1-9]{1}$";
    public static final String passwordRegex = "^(.+){8,}$";
    //https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
    public static final String emailRegex = "^(.+)@(.+)$";
}
