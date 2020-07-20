package edu.pdx.cs410J.sreev2;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 *
 * @author Sree Vandana
 */
public class Project1 {

  /**
   * arguments of type <code>String</code>
   * */
  private static final String arguments = "<customerName>  <callerNumber>  <calleeNumber>  <start Date>  <start time>  <end Date>  <end time> \n";

  /**
   * usage of type <code>String</code>
   * */
  private static final String usage = "Expected input\n" +
          "java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
          "[options] = [-print -README] can appear in any order\n" +
          "<args> are in this order:\n" +
           arguments+ "The <args> should be given in String.\n";

  /**
   * format of type <code>String</code>
   * */
  private  static final String format = "please follow these format's for phone number, Date and Time\n" +
          "Phone Number : nnn-nnn-nnnn\n" +
          "Date : mm/dd/yyyy\n" +
          "Time : hh:mm\n";

  /**
   * @param args
   *        [Options] <arguments>
   *        [-README -print] "<customerName>  <callerNumber>  <calleeNumber>  <start Date>  <start time>  <end Date>  <end time>
   *
   * 3 cases can happen in this main method
   * <var>length</var> = length of command line Arguments
   * CASE 1: length = 0
   * CASE 2: length <= 9
   *          [Sub case's]
   *          CASE i:   -README option present in given arguments as 1st or 2nd Argument
   *          CASE ii:  NO -README but -print Option is given as 1st Argument
   *          CASE iii: NO Options Provided
   * CASE 3: length > 9
   * */

  public static void main(String[] args) {

    var length = args.length;

    /*
    * CASE 1
    * */
    if (length == 0) {
      System.err.println("Missing command line arguments");
      System.err.println(usage);
      System.exit(1);
    }

    /*
    * CASE 2
    * */
    else if (length <= 9) {

      /*
      * CASE i
      * */
      if (Arrays.asList(args).contains("-README") &
              Arrays.asList(args).indexOf("-README") < 2) {

        printReadME();

        System.exit(1);
      }

      /*
       * CASE ii
       * -Check if all the required Arguments are given to perform -print Operation.
       *  (using validateArgs method)
       * */
      if(Arrays.asList(args).contains("-print") &
                Arrays.asList(args).indexOf("-print") < 1){
        if(validateArgs(args)){
          PhoneCall call = new PhoneCall(args);
          PhoneBill bill = new PhoneBill(args[1], call);
          System.out.println(args[1] + "'s Phone Call Information\n");
          System.out.println(call.toString());
          //System.out.println(bill.getPhoneCalls());

          System.exit(1);
        }
        }

      /*
      CASE iii
       * No options but too little arguments prints error
       * No options but too many arguments prints error
       * */
      if(!(Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2) &
      !(Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print")<2)){
        if(args.length < 7){
          System.err.println("You did not enter any options and the number of arguments entered are incomplete\n" +
                  "Required Arguments:\n" + arguments);
        }
        if(args.length > 7){
          System.err.println("You did not enter any options and the number of arguments entered are too many\n" +
                  "Required Arguments:\n" + arguments);
        }

        System.exit(1);
      }

      System.exit(1);

    }

    /*
    * CASE 3
    * 1st if : NO options and too much data
    * 2nd if : options given and too much data
    * */
    if(!(Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2) &
            !(Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print")<2)){
      System.err.println("You did not enter any options and the number of arguments entered are too many\n" +
              "Required Arguments:\n" + arguments);
    }
    if((Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2) ||
            (Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print")<2)){
      System.err.println("Seems like you have entered more than the required Arguments.\n"
              + "Required Arguments:\n" + arguments);
    }
    System.exit(0);

  }

  /**
   * @param args args of type <code>String[]</code>
   * @return boolean
   *
   * -print <arguments>name, CallerNum, CalleeNum, StartDate, StartTime, EndDate, EndTime</arguments>
   * 1st If: Check if all required Arguments are given
   * 2nd If: If all options are given;
   *         check if they are in correct Format
   *         if in correct Format - @return true
   *         else                 - @return false
   * */
  private static Boolean validateArgs(String[] args) {

    /*
    * 1st If
    * */
    if(args.length < 8 & (Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print") < 1)){
     //throw new IllegalNumberOfArgumentException(usage);
     // throw new NotSufficientArguments(usage);
      System.err.println("Not Sufficient number of arguments, to perform -print function\n"
              + "Required Arguments\n" + arguments);
      return false;
    }

    /*
    * 2nd If
    * */
    if(args.length == 8) {

      boolean phno1 = isValidatePhoneNumber(args[2]);
      if(!phno1){
        System.err.println("The CallerNumber you entered is not in correct format\n");
      }

      boolean phno2 = isValidatePhoneNumber(args[3]);
      if(!phno2){
       System.err.println("The CalleeNumber you entered is not in correct format\n");
      }

      boolean date1 = isValidateDate(args[4]);
      if(!date1){
        System.err.println("The StartDate you entered, is in wrong format\n");
      }

      boolean date2 = isValidateDate(args[6]);
      if(!date2){
        System.err.println("The EndDate you entered, is in wrong format\n");
      }

      boolean time1 = isValidateTime(args[5]);
      if(!time1){
        System.err.println("The StartTime you entered, is in wrong format\n");
      }

      boolean time2 = isValidateTime(args[7]);
      if(!time2){
        System.err.println("The EndTime you entered, is in wrong format\n");
      }

      if(phno1 & phno2 & date1 & date2 & time1 & time2){
        return true;
      }
      else{
        System.err.println(format);
        return false;
      }

    }

    /*
    * If more than required Arguments are given
    * */
    if(args.length > 8) {
      System.err.println("The Arguments required are 7. seems like you have entered more than required Arguments.\n" + arguments);
    }
    return false;
  }

  /**
   * Access the README.txt and print it when -README is given as an Option
   * */
  private static void printReadME() {

    try (InputStream readme = Project1.class.getResourceAsStream("README.txt")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line;
      //String line = reader.readLine();
      //System.out.println(line);
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  /**
   * @param phoneNumber
   * @return boolean
   *
   * Method for validating PhoneNumber Format (nnn-nnn-nnnn)
   * */
  private static boolean isValidatePhoneNumber(String phoneNumber){
    String regexPhoneNo = "^\\d{3}[\\s-]\\d{3}[\\s-]\\d{4}$";
    return Pattern.matches(regexPhoneNo, phoneNumber);
  }

  /**
   * @param date
   * @return boolean
   *
   * Method for validating Date Format (mm/dd/yyyy)
   * */
  private static boolean isValidateDate(String date){
    String regexDate = "^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}$";
    return Pattern.matches(regexDate, date);
  }

  /**
   * @param time
   * @return boolean
   *
   * Method for validating Time Format (hh:mm)
   * */
  private static boolean isValidateTime(String time){
    String regexTime = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
    return Pattern.matches(regexTime, time);
  }

}