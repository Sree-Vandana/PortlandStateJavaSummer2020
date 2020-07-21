package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;

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
public class Project3 {

    /**
     * arguments of type <code>String</code>
     * */
    private static final String arguments = "<customerName>  <callerNumber>  <calleeNumber>  <start Date>  <start time>  <AM/PM>  <end Date>  <end time>  <AM/PM>\n";

    /**
     * usage of type <code>String</code>
     * */
    private static final String usage = "Expected input\n" +
            "java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
            "[options] = [-pretty (prettyFile/-) -textFile fileName -print -README] can appear in any order, "
            + "but fileName must be given after -textFile option\n"
            + "file or - must be given after -pretty option, followed by"
            + "<args> are in this order:\n" +
            arguments+ "The <args> should be given in String.\n";

    /**
     * format of type <code>String</code>
     * */
    private  static final String format = "please follow these format's for phone number, Date and Time\n" +
            "Phone Number : nnn-nnn-nnnn where n=Number\n" +
            "Date : mm/dd/yyyy where mm=month; dd=date; yyyy=year\n" +
            "Time : hh:mm followed by AM/PM\n";

    /**
     * @param args
     *        [Options] <arguments>
     *        Options can be [-README -print -textFile FileName]
     *        Options can be given in any order.
     *        Arguments must be given in this order.
     *        <customerName>  <callerNumber>  <calleeNumber>  <start Date>  <start time>  <end Date>  <end time>
     *
     * 3 cases can happen in this main method
     * <var>length</var> = length of command line Arguments
     * CASE 1: length = 0
     * CASE 2: length <= 15
     *          [Sub case's]
     *          - README is given
     *          - Only -print
     *          - only -textFile
     *          - only -pretty
     *          - print & -textFile
     *          - print & -pretty
     *          - -pretty & -textFile
     *          - All 3 options given
     *          - No Option (if all arguments are given, validate their format)
     * CASE 3: length > 15
     * */
    public static void main(String[] args) {

        var length = args.length;

        if (length == 0) {
            System.err.println("Missing command line arguments");
            System.err.println(usage);
            System.exit(1);
        }

        else if (length <= 15) {

            //-README
            if (Arrays.asList(args).contains("-README") &
                    Arrays.asList(args).indexOf("-README") < 6) {
                try {
                    printReadME();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(1);
            }

            //only -print
            if(Arrays.asList(args).contains("-print") &
                    (Arrays.asList(args).indexOf("-print") < 5) &
                    !Arrays.asList(args).contains("-textFile") &
                    !Arrays.asList(args).contains("-pretty")){
                if(validateArgs(args)){
                    String[] args_m = {args[1], args[2], args[3], args[4]+" "+args[5]+" "+args[6], args[7]+" "+args[8]+" "+args[9]};
                    PhoneCall call = new PhoneCall(args_m);
                    PhoneBill bill = new PhoneBill(args_m[0], call);
                    System.out.println(args_m[0] + "'s Phone Call Information\n");
                    System.out.println(call.toString());
                }
                System.exit(1);
            }

            //only -textFile
            if(Arrays.asList(args).contains("-textFile") &
                    Arrays.asList(args).indexOf("-textFile") == 0 &
                    !(Arrays.asList(args).contains("-print")) &
                    !(Arrays.asList(args).contains("-pretty"))) {
                if (validateArgs(args)) {
                    //TextDumper
                    String name = args[2];
                    String[] args_m = {args[2], args[3], args[4], args[5]+" "+args[6]+" "+args[7], args[8]+" "+args[9]+" "+args[10]};
                    PhoneCall call = new PhoneCall(args_m);
                    PhoneBill bill = new PhoneBill(name, call);
                    writeIntoFile(args, bill);
                    System.out.println("The given phone call is successfully dumped into the textFile.");
                    PhoneBill readbill = readFromFile(args[Arrays.asList(args).indexOf("-textFile")+1]);
                    //System.out.println(readbill.getPhoneCalls());
                }
                System.exit(1);
            }

            //only -pretty
            if(Arrays.asList(args).contains("-pretty") &
                    Arrays.asList(args).indexOf("-pretty") == 0 &
                    !(Arrays.asList(args).contains("-print")) &
                    !(Arrays.asList(args).contains("-textFile"))) {
                if (validateArgs(args)) {
                    String name = args[2];
                    String[] args_m = {args[2], args[3], args[4], args[5]+" "+args[6]+" "+args[7], args[8]+" "+args[9]+" "+args[10]};
                    PhoneBill bill = new PhoneBill(name, new PhoneCall(args_m));
                    //PhoneBill readbill = readFromFile(args[Arrays.asList(args).indexOf("-textFile")+1]);
                    //final PhoneBill readbill = null;
                    pretty(args, bill.getCustomer(), bill);
                }
                System.exit(1);
            }

            //print & textFile
            if(Arrays.asList(args).contains("-print") & Arrays.asList(args).contains("-textFile") & !Arrays.asList(args).contains("-pretty")) {
                if (Arrays.asList(args).indexOf("-textFile") < 2 & Arrays.asList(args).indexOf("-print") < 3){
                    if (validateArgs(args)) {
                        if (fileNameGivenAfterTextFile(args)) {
                            String name = args[3];
                            String[] args_m = {args[3], args[4], args[5], args[6]+" "+args[7]+" "+args[8], args[9]+" "+args[10]+" "+args[11]};
                            PhoneCall call = new PhoneCall(args_m);
                            PhoneBill bill = new PhoneBill(name, call);
                            //TextDumper
                            writeIntoFile(args, bill);
                            System.out.println("The given phone call is successfully dumped into the textFile.\n");
                            System.out.println(args_m[0] + "'s latest Phone Call Information\n");
                            System.out.println(call.toString());
                            PhoneBill readbill = readFromFile(args[Arrays.asList(args).indexOf("-textFile") + 1]);
                        } else {
                            System.err.println("The \"FileName\" must be given only after -textFile Option\n"
                                    + "[options] = [-textFile fileName -pretty (fileName,-) -print -README] can appear in any order, "
                                    + "but fileName must be given after -textFile option\n");
                        }
                    }
                }
                else{
                    System.err.println("SomeThing is out of order\nPlease follow this usage\n" +usage);
                }
                System.exit(1);
            }

            //print & pretty
            if(Arrays.asList(args).contains("-print") & Arrays.asList(args).contains("-pretty") & !Arrays.asList(args).contains("-textFile")) {
                if (Arrays.asList(args).indexOf("-pretty") < 2 & Arrays.asList(args).indexOf("-print") < 3){
                    if (validateArgs(args)) {
                        if (fileNameOrHyphenGivenAfterPretty(args)) {
                            String name = args[3];
                            String[] args_m = {args[3], args[4], args[5], args[6]+" "+args[7]+" "+args[8], args[9]+" "+args[10]+" "+args[11]};
                            PhoneCall call = new PhoneCall(args_m);
                            PhoneBill bill = new PhoneBill(name, call);
                            //final PhoneBill readbill = null;
                            pretty(args, bill.getCustomer(), bill);
                            System.out.println(args_m[0] + "'s latest Phone Call Information\n");
                            System.out.println(call.toString());
                        } else {
                            System.err.println("The \"FileName\" or \"-\" must be given after -pretty Option\n"
                                    + "NOTE:\n[options] = [-pretty (fileName,-) -textFile fileName -print -README] can appear in any order, "
                                    + "but fileName or - must be given after -pretty option\n");
                        }
                    }
                }
                else{
                    System.err.println("Options must be given before arguments\n" +usage);
                }
                System.exit(1);
            }

            //textFile & pretty
            if(Arrays.asList(args).contains("-textFile") & Arrays.asList(args).contains("-pretty") & !Arrays.asList(args).contains("-print")) {
                if ((Arrays.asList(args).indexOf("-pretty") == 0 &
                        Arrays.asList(args).indexOf("-textFile") == 2) ||
                        (Arrays.asList(args).indexOf("-pretty") == 2 &
                                Arrays.asList(args).indexOf("-textFile") == 0)){
                    if (validateArgs(args)) {
                        if(fileNameOrPathDifferent(args)) {
                            if (fileNameOrHyphenGivenAfterPretty(args) & fileNameGivenAfterTextFile(args)) {
                                String name = args[4];
                                String[] args_m = {args[4], args[5], args[6], args[7] + " " + args[8] + " " + args[9], args[10] + " " + args[11] + " " + args[12]};
                                PhoneCall call = new PhoneCall(args_m);
                                PhoneBill bill = new PhoneBill(args_m[0], call);
                                //TextDumper
                                writeIntoFile(args, bill);
                                System.out.println("The given phone call is successfully dumped into the textFile.\n");
                                PhoneBill readbill = readFromFile(args[Arrays.asList(args).indexOf("-textFile") + 1]);
                                pretty(args, bill.getCustomer(), readbill);
                            } else {
                                System.err.println("The \"FileName\" must be given only after -textFile Option\n"
                                        + "[options] = [-pretty (fileName,-) -textFile fileName -print -README] can appear in any order, "
                                        + "but fileName must be given after -textFile option and\n"
                                        + "fileName or - must be given after -pretty option\n");
                            }
                        }else {
                            System.err.println("please provide different file name/path for -pretty option and -textFile option to avoid unwanted behaviour.\n");
                        }
                    }
                }
                else{
                    System.err.println("Something is out of order\nPlease follow this usage\n" +usage);
                }
                System.exit(1);
            }

            //all 3 opt given
            if(Arrays.asList(args).contains("-textFile") &
                    Arrays.asList(args).contains("-pretty") &
                    Arrays.asList(args).contains("-print")) {
                if ((Arrays.asList(args).indexOf("-pretty") < 5 &
                        Arrays.asList(args).indexOf("-textFile") < 5) &
                                Arrays.asList(args).indexOf("-print") < 5){
                    if (validateArgs(args)) {
                        if(fileNameOrPathDifferent(args)) {
                            if (fileNameOrHyphenGivenAfterPretty(args) & fileNameGivenAfterTextFile(args)) {
                                String name = args[5];
                                String[] args_m = {args[5], args[6], args[7], args[8] + " " + args[9] + " " + args[10], args[11] + " " + args[12] + " " + args[13]};
                                PhoneCall call = new PhoneCall(args_m);
                                PhoneBill bill = new PhoneBill(args_m[0], call);
                                //TextDumper
                                writeIntoFile(args, bill);
                                System.out.println("The given phone call is successfully dumped into the textFile.\n");
                                PhoneBill readbill = readFromFile(args[Arrays.asList(args).indexOf("-textFile") + 1]);
                                pretty(args, bill.getCustomer(), readbill);
                                System.out.println(args_m[0] + "'s latest Phone Call Information\n");
                                System.out.println(call.toString());
                            } else {
                                System.err.println("The \"FileName\" must be given only after -textFile Option\n"
                                        + "[options] = [-pretty (fileName,-) -textFile fileName -print -README] can appear in any order, "
                                        + "but fileName must be given after -textFile option and\n"
                                        + "fileName or - must be given after -pretty option\n");
                            }
                        }else {
                            System.err.println("please provide different file name/path for -pretty option and -textFile option to avoid unwanted behaviour.\n");
                        }
                    }
                }
                else{
                    System.err.println("Something is out of order\nPlease follow this usage\n" +usage);
                }
                System.exit(1);
            }

            //no option
            if(!(Arrays.asList(args).contains("-README")) &
                    !(Arrays.asList(args).contains("-print")) &
                    !(Arrays.asList(args).contains("-textFile"))){
                if(args.length < 9){
                    System.err.println("You did not enter any valid options and the number of arguments entered are incomplete\n" +
                            "Required Arguments:\n" + arguments);
                }
                if(args.length > 9){
                    System.err.println("You did not enter any valid options and the number of arguments entered are too many\n" +
                            "Required Arguments:\n" + arguments);
                }
                if(args.length == 9){
                    boolean validate = validateArgs(args);
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
        System.exit(1);

    }

    private static boolean fileNameOrPathDifferent(String[] args) {
        return !args[Arrays.asList(args).indexOf("-pretty")+1].equals(args[Arrays.asList(args).indexOf("-textFile")+1]);
    }

    private static void pretty(String[] args, String customer, final PhoneBill... bills) {
        try{
            var fullBill = new PhoneBill(customer, bills);
            var prettyPrinter = new PrettyPrinter(args[Arrays.asList(args).indexOf("-pretty")+1]);
            if (args[Arrays.asList(args).indexOf("-pretty")+1].equals("-") ){
                prettyPrinter.printOnStandardIO(fullBill);
            }else {
                prettyPrinter.dump(fullBill);
                System.out.println("Phone calls successfully entered into pretty file\n");
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    private static boolean fileNameOrHyphenGivenAfterPretty(String[] args) {
        int indexOfPretty = Arrays.asList(args).indexOf("-pretty");
        int indexOfPrint = Arrays.asList(args).indexOf("-print");
        return indexOfPrint != indexOfPretty + 1;
    }

    private static boolean fileNameGivenAfterTextFile(String[] args) {
        int indexOftextFile = Arrays.asList(args).indexOf("-textFile");
        int indexOfPrint = Arrays.asList(args).indexOf("-print");
        return indexOfPrint != indexOftextFile + 1;
    }

    /**
     * @param  fileName <type>String</type>
     *                  the name or path of the file from which data is read
     * @return bill <class>PhoneBill</class>
     *                  returns PhoneBill object which has all the phone calls in it
     * @throws Exception
     *                  can throw an IOException while reading the file
     * */
    private static PhoneBill readFromFile(String fileName) {
        try {
            TextParser txtParser = new TextParser(fileName);
            PhoneBill bill = txtParser.parse();
            return bill;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /**
     * @param args <type>String</type>
     *             the command line arguments, phone call information entered by the user
     * @param bill <class>PhoneBill</class>
     *             the PhoneBill Object which holds the phone call information, that need to be written into file
     * @throws Exception
     *             might throw IOExeption while trying to write to a file
     * */
    private static void writeIntoFile(String[] args, PhoneBill bill) {
        try {
            TextDumper txtDumper = new TextDumper(args[Arrays.asList(args).indexOf("-textFile")+1]);
            txtDumper.dump(bill);
        } catch (Exception e) {
            System.err.println("Error occured when trying to write to file");
            //e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @param args of type <code>String[]</code>
     *             Command line arguments, PhoneCall information entered by an user
     * @return boolean
     * This method checks if the number of given arguments are valid or not for the given option
     * */
    private static Boolean validateArgs(String[] args) {

        int l = 0;

        //only -print
        if(Arrays.asList(args).contains("-print") &
                ! Arrays.asList(args).contains("-textFile") &
                !Arrays.asList(args).contains("-pretty")) {
            l = 10;
        }

        //only -textFile
        //only pretty
        if((Arrays.asList(args).contains("-textFile") &
                !Arrays.asList(args).contains("-print") &
                !Arrays.asList(args).contains("-pretty")) ||
                (Arrays.asList(args).contains("-pretty") &
                        !Arrays.asList(args).contains("-textFile") &
                        !Arrays.asList(args).contains("-print"))){
            l = 11;
        }

        //if(Arrays.asList(args).contains("-print") & Arrays.asList(args).contains("-textFile"))
        //-print + -textFile
        //-print + -pretty
        if((Arrays.asList(args).contains("-print") &
                Arrays.asList(args).contains("-textFile") &
                !Arrays.asList(args).contains("-pretty")) ||
                (Arrays.asList(args).contains("-print") &
                        Arrays.asList(args).contains("-pretty") &
                        !Arrays.asList(args).contains("-textFile"))){
            l = 12;
        }

        //-pretty + -textFile
        if(Arrays.asList(args).contains("-pretty") &
                Arrays.asList(args).contains("-textFile") &
                !Arrays.asList(args).contains("-print")){
            l = 13;
        }

        //-pretty + -textFile + -print
        if(Arrays.asList(args).contains("-pretty") &
                Arrays.asList(args).contains("-textFile") &
                Arrays.asList(args).contains("-print")){
            l = 14;
        }

        //no option
        if(!(Arrays.asList(args).contains("-README")) &
                !(Arrays.asList(args).contains("-print")) &
                !(Arrays.asList(args).contains("-textFile")) &
                !(Arrays.asList(args).contains("-pretty"))){
            l = 9;
        }

//& (Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print") == 0 )
        if(args.length < l){
                System.err.println("Not Sufficient number of arguments provided,\n"
                        + "Required Arguments:\n" + arguments
                        + "\nNOTE: when giving [-textFile] option make sure to give <fileName> next to it\n"
                        + "NOTE: when giving [-pretty] option make sure to give (fileName/-) next to it");

            return false;
        }

        if(args.length == l) {
            //only -print
            if(l == 10) {
                return valiadteArgsFormats(args, 2, 3, 4, 7, 5, 6, 8, 9);
            }
            //only -textFile
            //only pretty
            if(l == 11) {
                return valiadteArgsFormats(args, 3, 4, 5, 8, 6, 7, 9, 10);
            }
            //-print + -textFile
            //-print + -pretty
            if(l == 12) {
                return valiadteArgsFormats(args, 4, 5, 6, 9, 7, 8, 10, 11);
            }
            //-pretty + -textFile
            if(l == 13) {
                return valiadteArgsFormats(args, 5, 6, 7, 10, 8, 9, 11, 12);
            }
            //-pretty + -textFile + -print
            if(l == 14) {
                return valiadteArgsFormats(args, 6, 7, 8, 11, 9, 10, 12, 13);
            }
            //No option
            if(l == 9){
                return valiadteArgsFormats(args, 1, 2, 3, 6, 4, 5, 7, 8);
            }

        }

        if(args.length > l) {
            System.err.println("The Arguments required are 9. seems like you have entered more than required Arguments.\n" + arguments);
        }
        return false;
    }

    /**
     * @param args <type>String[]</type>
     *             these are command line arguments
     * @param index <type>int...</type>
     *              send the index values of arguments that need to be validated
     * @return Boolean
     * this method validate the format of given command line arguments
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

        boolean time1 = isValidateTime(args[index[4]] + " " + args[index[5]]);
        if(!time1){
            System.err.println("The StartTime you entered, is in wrong format\n");
        }

        boolean time2 = isValidateTime(args[index[6]] + " " + args[index[7]]);
        if(!time2){
            System.err.println("The EndTime you entered, is in wrong format\n");
        }

        if(phno1 & phno2 & date1 & date2 & time1 & time2 ){
            return true;
        }
        else{
            System.err.println(format);
            return false;
        }
    }

    /**
     * This method Access the README.txt and print it when -README is given as an Option
     * @throws IOException
     *          this method can result in IOException, while reading README file.
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
        String regexDate = "^(0[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}$";
        return Pattern.matches(regexDate, date);
    }

    /**
     * @param time
     *        the time shuld be of the format hh:mm
     * @return boolean
     *Method for validating Time Format (hh:mm)
     * */
    private static boolean isValidateTime(String time){
        String regexTime = "^(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$";
        //"^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"
        return Pattern.matches(regexTime, time);
    }

}
