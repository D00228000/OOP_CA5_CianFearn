package com.dkit.oopca5.client;

/**
 * Name: Cían Fearn
 * Student Number: D00228000
 */

public class RegexChecker
{
    public static final String CAORegex = "\\d{8}";
    public static final String DOBRegex = "^[1920-9999]{4}\\-[0-3]{1}[1-9]{1}\\-[0-1]{1}[1-9]{1}$";
    public static final String passwordRegex = "^(.+){8,}$";
    //https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
    public static final String emailRegex = "^(.+)@(.+)$";
}
