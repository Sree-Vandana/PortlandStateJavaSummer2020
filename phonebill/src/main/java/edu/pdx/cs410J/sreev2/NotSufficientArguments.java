package edu.pdx.cs410J.sreev2;

public class NotSufficientArguments extends RuntimeException {
    NotSufficientArguments(String usage){
        super(usage);
    }
}
