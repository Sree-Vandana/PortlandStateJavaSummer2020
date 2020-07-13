package edu.pdx.cs410J.sreev2;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 *
 * @author Sree Vandana
 */
public class Project2 {

    /**
     * arguments of type <code>String</code>
     * */
    private static final String arguments = "<customerName>  <callerNumber>  <calleeNumber>  <start Date>  <start time>  <end Date>  <end time> \n";

    /**
     * usage of type <code>String</code>
     * */
    private static final String usage = "Expected input\n" +
            "java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
            "[options] = [-textFile fileName -print -README] can appear in any order, "
            + "but fileName must be given after -textFile option\n" +
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
     * CASE 2: length <= 11
     *          [Sub case's]
     *          CASE i:   -README option present in given arguments as 1st, 2nd, 3rd or 4th Argument
     *          CASE ii:  NO -README but -print Option is given as 1st Argument
     *          CASE iii: NO Options Provided
     * CASE 3: length > 9
     * */

    public static void main(String[] args) {

        var length = args.length;

        if (length == 0) {
            System.err.println("Missing command line arguments");
            System.err.println(usage);
            System.exit(1);
        }

        else if (length <= 11) {

            if (Arrays.asList(args).contains("-README") &
                    Arrays.asList(args).indexOf("-README") < 4) {
                try {
                    printReadME();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(1);
            }

            if(Arrays.asList(args).contains("-print") &
                    (Arrays.asList(args).indexOf("-print") == 0) &
                    !Arrays.asList(args).contains("-textFile")){
                if(validateArgs(args)){
                    /*try {
                        //TextParser
                        TextParser textParser = new TextParser(args[Arrays.asList(args).indexOf("-textFile") + 1]);
                        System.out.println(textParser.parse());
                    }catch (Exception ex){
                        System.err.println(ex.getMessage());
                        System.exit(1);
                    }*/
                    PhoneCall call = new PhoneCall(args, "p");
                    PhoneBill bill = new PhoneBill(args[1], call);
                    System.out.println(args[1] + "'s Phone Call Information\n");
                    System.out.println(call.toString());
                    System.exit(1);
                }
            }

            if(Arrays.asList(args).contains("-textFile") &
                    Arrays.asList(args).indexOf("-textFile") == 0 &
                        !(Arrays.asList(args).contains("-print"))) {
                        if (validateArgs(args)) {
                            //TextDumper
                            String name = args[2];
                            PhoneCall call = new PhoneCall(args, "t");
                            PhoneBill bill = new PhoneBill(name, call);
                            writeIntoFile(args, bill);
                        }

                        System.exit(0);
            }

            if(Arrays.asList(args).contains("-print") & Arrays.asList(args).contains("-textFile")){
                 if(validateArgs(args)){
                        PhoneCall call = new PhoneCall(args, "tp");
                        PhoneBill bill = new PhoneBill(args[3], call);
                        //TextDumper
                        writeIntoFile(args, bill);
                        System.out.println(args[3] + "'s Phone Call Information\n");
                        System.out.println(call.toString());
                    }
                    System.exit(1);
                //}
            }

            if(!(Arrays.asList(args).contains("-README")) &
                    !(Arrays.asList(args).contains("-print")) &
                    !(Arrays.asList(args).contains("-textFile"))){
                if(args.length < 7){
                    System.err.println("You did not enter any valid options and the number of arguments entered are incomplete\n" +
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

        if(! Arrays.asList(args).contains("-README")  &
                !Arrays.asList(args).contains("-print") &
                !Arrays.asList(args).contains("-textFile")){
            System.err.println("You did not enter any valid options and the number of arguments entered are too many\n" +
                    "Required Arguments:\n" + arguments);
        }
        if(Arrays.asList(args).contains("-README") ||
                Arrays.asList(args).contains("-print") ||
                Arrays.asList(args).contains("-textFile")){
            System.err.println("Seems like you have entered more than the required Arguments.\n"
                    + "You can enter:\n" + usage);
        }
        System.exit(0);

    }

    private static void writeIntoFile(String[] args, PhoneBill bill) {
        //TextDumper txtDumper = null;
        try {
            System.out.println(args[Arrays.asList(args).indexOf("-textFile")+1]);
            TextDumper txtDumper = new TextDumper(args[Arrays.asList(args).indexOf("-textFile")+1]);
            txtDumper.dump(bill);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        int l;

        if(Arrays.asList(args).contains("-textFile") & !Arrays.asList(args).contains("-print")){
            l = 9;
        }
        else if(Arrays.asList(args).contains("-print") & ! Arrays.asList(args).contains("-textFile")) {
            l = 8;
        }
        //if(Arrays.asList(args).contains("-print") & Arrays.asList(args).contains("-textFile"))
        else{
            l = 10;
        }
//& (Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print") == 0 )
        if(args.length < l){
            //throw new IllegalNumberOfArgumentException(usage);
            // throw new NotSufficientArguments(usage);
            if(l == 8){
                System.err.println("Not Sufficient number of arguments provided,\n"
                        + "Required Arguments\n" + arguments);
            }
            else{
                System.err.println("Not Sufficient number of arguments provided,\n"
                        + "Required Arguments:\n" + arguments
                        + "\nNOTE: when giving [-textFile] option make sure to give <fileName> next to it\n");
            }

            return false;
        }

        if(args.length == l) {
            if(l == 8) {
                return valiadteArgsFormats(args, 2, 3, 4, 6, 5, 7);
            }
            //only -textFile
            if(l == 9) {
                return valiadteArgsFormats(args, 3, 4, 5, 7, 6, 8);
            }
            //both -textFile and -print
            if(l == 10) {
                return valiadteArgsFormats(args, 4, 5, 6, 8, 7, 9);
            }

        }

        if(args.length > l) {
            System.err.println("The Arguments required are 7. seems like you have entered more than required Arguments.\n" + arguments);
        }
        return false;
    }

    /**
     * validate the format of given arguments
     * */
    private static Boolean valiadteArgsFormats(String[] args, int... index) {

        boolean phno1 = isValidatePhoneNumber(args[index[0]]);
        if(!phno1){
            System.err.println("The CallerNumber you entered is not in correct format\n");
        }

        boolean phno2 = isValidatePhoneNumber(args[index[1]]);
        if(!phno2){
            System.err.println("The CalleeNumber you entered is not in correct format\n");
        }

        boolean date1 = isValidateDate(args[index[2]]);
        if(!date1){
            System.err.println("The StartDate you entered, is in wrong format\n");
        }

        boolean date2 = isValidateDate(args[index[3]]);
        if(!date2){
            System.err.println("The EndDate you entered, is in wrong format\n");
        }

        boolean time1 = isValidateTime(args[index[4]]);
        if(!time1){
            System.err.println("The StartTime you entered, is in wrong format\n");
        }

        boolean time2 = isValidateTime(args[index[5]]);
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

    /**
     * Access the README.txt and print it when -README is given as an Option
     * */
    private static void printReadME() throws IOException {

        try (InputStream readme = Project1.class.getResourceAsStream("README.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * @param phoneNumber
     *        the phone number should be of the format  nnn-nnn-nnnn
     * @return boolean
     * Method for validating PhoneNumber Format (nnn-nnn-nnnn)
     * */
    private static boolean isValidatePhoneNumber(String phoneNumber){
        String regexPhoneNo = "^\\d{3}[\\s-]\\d{3}[\\s-]\\d{4}$";
        return Pattern.matches(regexPhoneNo, phoneNumber);
    }

    /**
     * @param date
     *        the date should be of the format mm/dd/yyyy
     * @return boolean
     * Method for validating Date Format (mm/dd/yyyy)
     * */
    private static boolean isValidateDate(String date){
        String regexDate = "^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}$";
        return Pattern.matches(regexDate, date);
    }

    /**
     * @param time
     *        the time shuld be of the format hh:mm
     * @return boolean
     *
     * Method for validating Time Format (hh:mm)
     * */
    private static boolean isValidateTime(String time){
        String regexTime = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        return Pattern.matches(regexTime, time);
    }

}
