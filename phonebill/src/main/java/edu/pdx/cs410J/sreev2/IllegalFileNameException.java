package edu.pdx.cs410J.sreev2;

import java.io.IOException;

/**
 * This Exception is thrown when user gives an file name that is not accepted.
 *takes the error statements and prints it to console
 * */
public class IllegalFileNameException extends IOException {
    public IllegalFileNameException(String s) {
        super();
        System.err.println(s);
    }
}

