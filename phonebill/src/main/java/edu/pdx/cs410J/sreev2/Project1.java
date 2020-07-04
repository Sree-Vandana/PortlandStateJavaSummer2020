package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

/**
 * The main class for the CS410J Phone Bill Project
 *
 * @author Sree Vandana
 */
public class Project1 {

  private static final int minArgsLen = 7;

  public static void main(String[] args) {

    String usage = "Expected input\n" +
            "java edu.pdx.cs410J.<login-id>.Project1 [-print -README] <args>\n" +
            "<args> are (in this order):\n" +
            "<customer>  <callerNumber>  <calleeNumber>  " +
            "<start Date>  <start time>  " +
            "<end Date>  <end time>  \n" +
            "[options] may appear in any order\n" +
            "The <args> should be given in String format." +
            "please give the date in format \"mm/dd/yyyy\" " +
            "and time in format \"hh:mm\"";

    var length = args.length;

    /*
    * If no Arguments are passed, then send error message and exit with exit-code 1
    * */

    if (length == 0) {
      System.err.println("Missing command line arguments");
      System.err.println(usage);
      System.exit(1);
    }

    else if (length <= 9) {

      /*
       * If in given arguments -README is present at locations 1 or 2
       * then print the README.Txt file.
       * DO NOT perform any operations.
       * */

      if (Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2) {
        try (InputStream readme = Project1.class.getResourceAsStream("README.txt")) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String line = reader.readLine();
          System.out.println(line);
        } catch (IOException e) {
          System.err.println(e);
        }
        System.exit(1);
      }
      //PhoneCall call = new PhoneCall(args);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      //PhoneBill bill = new PhoneBill(args[0], call);
      //System.out.println("call = "+call);
      //System.out.println("bill = "+ bill);
      //System.err.println("Missing command line arguments");
      //for (String arg : args) {
      //  System.out.println(arg);
      //}

      /*
      * If README not present and print is present.
      * then check if other required arguments are passed correctly and
      * print the discription about the call.
      * */
      else {

        System.exit(0);
      }

    }

  }
}