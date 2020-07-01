package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1{
  private static final int min_arg_len = 7;

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
    //String name = args[0];
    //System.out.println(args.length);
    //System.out.println(args[args.length - min_arg_len]);
    System.exit(1);
  }

}