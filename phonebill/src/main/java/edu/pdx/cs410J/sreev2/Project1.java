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
import edu.pdx.cs410J.sreev2.NotSufficientArguments;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 *
 * @author Sree Vandana
 */
public class Project1 {

  private static final int minArgsLen = 7;
  private static final String usage = "Expected input\n" +
          "java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
          "<args> are (in this order):\n" +
          "<customer>  <callerNumber>  <calleeNumber>  " +
          "<start Date>  <start time>  " +
          "<end Date>  <end time>  \n" +
          "[options] = [-print -README] can appear in any order\n" +
          "The <args> should be given in String format." +
          "please give the date in format \"mm/dd/yyyy\" " +
          "and time in format \"hh:mm\"";

  public static void main(String[] args) {

    var length = args.length;

    /*
    * If no Arguments are passed, then send error message and exit with exit-code 1
    * */
    if (length == 0) {
      System.err.println("Missing command line arguments");
      System.err.println(usage);
      System.exit(1);
    }
/*
* CASE 1: README
* CASE 2: NO README
* */
    else if (length <= 9) {

      if (Arrays.asList(args).contains("-README") &
              Arrays.asList(args).indexOf("-README") < 2) {
        printReadME();
        System.exit(1);
      }

      else if(Arrays.asList(args).contains("-print") &
                Arrays.asList(args).indexOf("-print") < 1 &
              validateArgs(args)){
          System.exit(1);
        }

    }
    // [no option] given and [print + no_arguments] and
    // [print + few less arguments]

    System.exit(0);
  }

  private static Boolean validateArgs(String[] args) {
    String regexName = "[a-zA-Z0-9.-]*";
    String regexPhoneNo = "^\\d{3}-\\d{3}-\\d{4}$";
    String regexDate = "/^(0?[1-9]|1[0-2])\\/(0?[1-9]|1\\d|2\\d|3[01])\\/(19|20)\\d{2}$/";
    String regexTime = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";

    if(args.length < 8){
     //throw new IllegalNumberOfArgumentException(usage);
     // throw new NotSufficientArguments(usage);
      return false;
    }

    if(args.length == 8) {
      if (Pattern.matches(regexName, args[1]) &
              Pattern.matches(regexPhoneNo, args[2]) &
              Pattern.matches(regexPhoneNo, args[3])){
        return true;
      }
      else {
        //throw new IllegalFormatOfDataException();
        return false;
      }
    }
    //System.err.println("Given Name in in wrong Fromat (should not contain ant special charecters)\nit can contain - and  _ and Numbers");
    return false;
  }

  private static void printReadME() {
    try (InputStream readme = Project1.class.getResourceAsStream("README.txt")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      System.out.println(line);
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}