package edu.pdx.cs410J.sreev2;

import java.io.IOException;

public class IllegalFileNameException extends IOException {
    public IllegalFileNameException(String s) {
        super();
        System.err.println(s);
    }
}

