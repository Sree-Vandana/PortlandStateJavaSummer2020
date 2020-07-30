package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    /**
     * MISSING_ARGS of type <code>String</code>
     * */
    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * format of type <code>String</code>
     * */
    public static final String format = "Format:\nFor phone number: nnn-nnn-nnnn where n =  number [0-9]\n" +
                                        "For Date: MM/dd/yyyy\n" +
                                        "For time: hh:mm am/pm\n";

    /**
     * validHostAndPort of type <code>String</code>
     * */
    public static final String validHostAndPort = "-host <hostname> -port <portNumber>";

    /**
     * validPrintArgs of type <code>String</code>
     * */
    public static final String validPrintArgs = "<customerName> <CallerPhoneNumber> <CalleePhoneNumber> <startDate> <startTime> <am/pm> <endDate> <endTime> <am/pm>";

    /**
     * validSearchArgs of type <code>String</code>
     * */
    public static final String validSearchArgs = "<customerName> <startDate> <startTime> <am/pm> <endDate> <endTime> <am/pm>\"";

    /**
     * this main method validates the arguments give, before it sends to the server.
     * And performs the appropreate action based on the option provided.
     * @param args of type <code>String[]</code>
     *             The phone call information, <customerName> <CallerPhoneNumber> <CalleePhoneNumber> <startDate> <startTime> <am/pm> <endDate> <endTime> <am/pm>
     * */
    public static void main(String[] args) {

        var length = args.length;

        if (length == 0) {
            usage("Missing command line arguments");
            System.exit(1);
        }

        //-README
        if (Arrays.asList(args).contains("-README") &
                Arrays.asList(args).indexOf("-README") < 7) {
            try {
                printReadME();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
        //options
        else if (validateData(args)) {

            String hostName = args[Arrays.asList(args).indexOf("-host") + 1];
            int port = Integer.parseInt(args[Arrays.asList(args).indexOf("-port") + 1]);

            PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

            ArrayList<String> list = new ArrayList<>();
            list.addAll(List.of(args));
            list.remove(list.get(list.indexOf("-host") + 1));
            list.remove(list.get(list.indexOf("-port") + 1));
            list.remove("-host");
            list.remove("-port");

            try {
                //customer name
                if (list.size() == 1) {
                    System.out.println(client.printEntirePhoneBill(list.get(0)));
                    System.exit(0);
                }
                //-search
                else if (list.contains("-search")) {
                    list.removeAll(Collections.singleton("-search"));
                    System.out.println(client.searchForPhoneCalls(new String[]{list.get(0), list.get(1)+" "+list.get(2)+" "+list.get(3), list.get(4)+" "+list.get(5)+" "+list.get(6)}));
                    System.exit(0);
                }
                //print
                else {
                    boolean pcheck = list.removeAll(Collections.singleton("-print"));
                    System.out.println(client.addPhoneCall(new String[]{list.get(0), list.get(1), list.get(2), list.get(3) +" "+ list.get(4) +" "+ list.get(5), list.get(6) +" "+ list.get(7) +" "+ list.get(8)}));
                    if(pcheck) {
                        PhoneCall call = new PhoneCall(new String[]{list.get(0), list.get(1), list.get(2), list.get(3) +" "+ list.get(4) +" "+ list.get(5), list.get(6) +" "+ list.get(7) +" "+ list.get(8)});
                        System.out.println(call.toString());
                    }
                    System.exit(0);
                }
            }
            catch (ConnectException ce){
                System.err.println(ce.getMessage());
                System.err.println("Refused to Connect to the Server "+hostName+": "+port);
                System.exit(1);
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.exit(0);

        }
    }

    /**
     * This method validates the given Arguments and checks if they are given in correct format or not.
     * @param args <type>String</type>
     *             PhoneCall information
     * @return boolean
     * */
    private static boolean validateData(String[] args) {
        int length = args.length;

        //check for -host and -port
        if(!Arrays.asList(args).contains("-host") || !Arrays.asList(args).contains("-port")){
            usage("Must include a host and Port\n");
        }
        else if(Arrays.asList(args).indexOf("host") > 4 || Arrays.asList(args).indexOf("-port") > 4){
            usage("command line is out of order\n");
        }
        else if(Arrays.asList(args).get(Arrays.asList(args).indexOf("-host")+1).matches("^-(port|print|search)$")){
            System.err.println("please provide a valid host name\n"+validHostAndPort);
            System.exit(1);
        }
        try {
            Integer.parseInt(Arrays.asList(args).get(Arrays.asList(args).indexOf("-port") + 1));
        }catch (NumberFormatException ex){
            System.err.println("Port number should be a number\n"+validHostAndPort);
            System.exit(1);
        }

        //search and print
        if(Arrays.asList(args).contains("-print") & Arrays.asList(args).contains("-search")){
            System.err.println("print and search should not be given together, as they require different input arguments");
            System.exit(1);
        }

        //only search
        else if(Arrays.asList(args).contains("-search") &
                !(Arrays.asList(args).indexOf("-search")<5)) {
            usage("search should be given before arguments\n");
        }
        else if(Arrays.asList(args).contains("-search") & length<12) {
            System.err.println("missing required Arguments\nFor Search the required arguments are\n"+validSearchArgs);
            System.exit(1);
        }
        else if(Arrays.asList(args).contains("-search") & length>12) {
            System.err.println("given too many argumnets\nFor Search the required arguments are\n"+validSearchArgs);
            System.exit(1);
        }
        if(Arrays.asList(args).contains("-search")){
            boolean scheck = checkFormatOfArguments(6, args, 0, 0, 6, 7, 8, 9, 10, 11);
            if(scheck){
                validateStartAndEndTimes(args[6]+" "+args[7]+" "+args[8], args[9]+" "+args[10]+" "+args[11]);
            }
            else{
                System.exit(1);
            }
        }

        //only print
        if(Arrays.asList(args).contains("-print") &
                !(Arrays.asList(args).indexOf("-print")<5)) {
            usage("print should be given before arguments\n");
        }
        else if(Arrays.asList(args).contains("-print") & length<14) {
            System.err.println("missing required Arguments\nFor Print, the rquired arguments are\n"+validPrintArgs);
            System.exit(1);
        }
        else if(Arrays.asList(args).contains("-print") & length>14) {
            System.err.println("given too many argumnets\nFor Print, the rquired arguments are\n"+validPrintArgs);
            System.exit(1);
        }
        if(Arrays.asList(args).contains("-print")) {
            boolean pcheck = checkFormatOfArguments(8, args, 6, 7, 8, 9, 10, 11, 12, 13);
            if(pcheck){
                validateStartAndEndTimes(args[8]+" "+args[9]+" "+args[10], args[11]+" "+args[12]+" "+args[13]);
            }
            else{
                System.exit(1);
            }
        }

        //no option and no customername and too few args
        if(!Arrays.asList(args).contains("-print") &
                !Arrays.asList(args).contains("-search") & length < 5){
            System.err.println("-print or -search or customerName are not given and no arguments provided!\n");
            System.exit(1);
        }

        //no options, gave other args along with customer name; but insufficient args
        if(!Arrays.asList(args).contains("-print") &
                !Arrays.asList(args).contains("-search") & (length > 5 & length < 13)){
            System.err.println("-print or -search are not given and insufficient arguments provided!\n");
            System.exit(1);
        }

        //no option and correct all args
        if(!Arrays.asList(args).contains("-print") &
                !Arrays.asList(args).contains("-search") & (length == 13)){
            if(checkFormatOfArguments(8, args, 5,6,7,8,9,10,11,12)){
                validateStartAndEndTimes(args[7]+" "+args[8]+" "+args[9], args[10]+" "+args[11]+" "+args[12]);
            }
            else{
                System.exit(1);
            }
        }

        //no option but too many argumemnts
        if(!Arrays.asList(args).contains("-print") &
                !Arrays.asList(args).contains("-search") & length > 13){
            System.err.println("did not give -print or -search and gave too many arguments\n");
            System.exit(1);
        }
        return true;
    }

    /**
     * this method checks for the format of the given arguments
     * @param len <type>int</type>
     *            length of arguments being sent
     * @param args <type>String[]</type>
     *             the Arguments
     * @param index <type>int...</type>
     *              index of the atguments for validation
     * @return boolean
     * */
    private static boolean checkFormatOfArguments(int len, String[] args, int... index) {

        boolean phno1 = true;
        boolean phno2 = true;
        if(len!=6) {
            phno1 = isValidatePhoneNumber(args[index[0]]);
            if (!phno1) {
                System.err.println("The CallerNumber you entered is not in correct format\n");
            }

            phno2 = isValidatePhoneNumber(args[index[1]]);
            if (!phno2) {
                System.err.println("The CalleeNumber you entered is not in correct format\n");
            }
        }

        boolean date1 = isValidateDate(args[index[2]]);
        if(!date1){
            System.err.println("The StartDate you entered, is in wrong format\n");
        }

        boolean time1 = isValidateTime(args[index[3]]);
        if(!time1){
            System.err.println("The StartTime you entered, is in wrong format\n");
        }

        boolean marker1 = isValidateMarker(args[index[4]]);
        if(!marker1){
            System.err.println("The StartTime marker you entered, is in wrong format\n");
        }

        boolean date2 = isValidateDate(args[index[5]]);
        if(!date2){
            System.err.println("The EndDate you entered, is in wrong format\n");
        }

        boolean time2 = isValidateTime(args[index[6]]);
        if(!time2){
            System.err.println("The EndTime you entered, is in wrong format\n");
        }

        boolean marker2 = isValidateMarker(args[index[7]]);
        if(!marker2){
            System.err.println("The EndTime marker you entered, is in wrong format\n");
        }

        if(phno1 & phno2 & date1 & date2 & time1 & time2 & marker1 & marker2){
            // return true;
            return true;
        }
        else{
            System.err.println(format);
            return false;
        }
    }

    /**
     *  Method for validating PhoneNumber Format (nnn-nnn-nnnn)
     * @param phoneNumber
     *        the phone number should be of the format  nnn-nnn-nnnn
     * @return boolean
     * */
    private static boolean isValidatePhoneNumber(String phoneNumber){
        String regexPhoneNo = "^\\d{3}[\\s-]\\d{3}[\\s-]\\d{4}$";
        return Pattern.matches(regexPhoneNo, phoneNumber);
    }

    /**
     *  Method for validating Date Format (mm/dd/yyyy)
     * @param date
     *        the date should be of the format mm/dd/yyyy
     * @return boolean
     * */
    private static boolean isValidateDate(String date){
        String regexDate = "^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}$";
        return Pattern.matches(regexDate, date);
    }

    /**
     * Method for validating Time Format (hh:mm)
     * @param time
     *        the time should be of the format hh:mm
     * @return boolean
     * */
    private static boolean isValidateTime(String time){
        String regexTime = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        return Pattern.matches(regexTime, time);
    }

    /**
     * Method for validating Time Format (am/pm)
     * @param marker
     *        the time should be of the format amAM/pmPM
     * @return boolean
     * */
    private static boolean isValidateMarker(String marker) {
        String regexMarker = "^[PpAa][Mm]$";
        return Pattern.matches(regexMarker, marker);
    }

    /**
     * This method validates if the given start and end time are given in correct order or not.
     * @param startTimeTest <type>String</type>
     *                      start time
     * @param endTimeTest <type>String</type>
     *                    end Time
     * */
    private static void validateStartAndEndTimes(String startTimeTest, String endTimeTest) {
        Date startTime = getDateAndTimeInDate(startTimeTest);
        Date endTime = getDateAndTimeInDate(endTimeTest);
        if (!startTime.before(endTime)){
            System.err.println("Start date and time can not be equals or after the end date and "
                    + "time of the phone call");
            System.exit(1);
        }
    }

    /**
     * this method parses the string date into <class>Date</class>
     * @param timeTest <type>String</type>
     *                 start or end time
     * @return Date
     * */
    private static Date getDateAndTimeInDate(String timeTest) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date1 = formatter1.parse(timeTest);
            return date1;
        } catch (ParseException e) {
            System.err.println("Date parsing error");
            System.exit(1);
        }

        return null;
    }


    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 [options] [arguments]");
        err.println("  [options]    [-host hostName -port portNumber -search -print -README]");
        err.println("Options Can be given in any Order, But should be given before Arguments\n"+
                    "-host hostname - Host computer on which the server runs\n"+
                    "-port port     - Port on which the server is listening\n" +
                    "-search        - Phone calls should be searched for\n" +
                    "-print         - Prints a description of the new phone call\n" +
                    "-README        - Prints a README for this project and exits");
        err.println();
        err.println("[Arguments]  [Customer callerNumber CalleeNumber Start End]");
        err.println("Arguments are in this order\n"+
                    "customer      -  Person whose phone bill we’re modeling\n" +
                    "callerNumber  -  Phone number of caller\n" +
                    "calleeNumber  -  Phone number of person who was called\n" +
                    "start         -  Date and time call began (MM/dd/yyyy hh:mm am/pm)\n" +
                    "end           -  Date and time call ended (MM/dd/yyyy hh:mm am/pm)\n");
        err.println();
        err.println("NOTE:\n"+"1. Donot give -search and -print together\n"+
                    "2. host and port must be specified.\n"+
                    "3. Time must follow 12-hour clock format and specify the start and end as (MM/dd/yyyy hh:mm am/pm)\n"+
                    "4. provide -search followed  by <customer>, <start> and <end> to get dates between dates start and end\n"+
                    "5. just providing customer name prints entire phonebill of that customer of it exists.(this must be follwed by -host and -port options).");

        System.exit(1);
    }

    /**
     * This method Access the README.txt and print it when -README is given as an Option
     * @throws IOException
     *          this method can result in IOException, while reading README file.
     * */
    private static void printReadME() throws IOException {
        String readme = "This is a README!\n"+
                        "Name: Sree Vandana\n"+
                        "Project 4 PhoneBill Web Application.\n" +
                "This simple program posts PhoneCalls to the server. And gets the stored phone bills of different customers.\n" +
                "Below are the functionalities it provides\n"+
                        "[Options][Arguments]\n"+
                        "[options] = [-host hostName -port portNumber -search -print -README]"+
                        "[Arguments] = [Customer callerNumber CalleeNumber Start End]\n" +
                "\nThe client can perform several functions:\n" +
                "– Add a phone call to the server: Provide all Arguments along with host and port number.\n" +
                "– Add a phone call to the server & display latest call information: Provide All required arguments along with -print option and host and port\n" +
                "– Search for the calls begun between two times: provide -search option with "+validSearchArgs+"\n" +
                "– Pretty print all phone calls in a phone bill: This can be done by just providing customer name whose phone bill you want to display.\n" +
                "\nNOTE:\n"+
                "1. Donot give -search and -print together\n"+
                "2. host and port must be specified.\n"+
                "3. Time must follow 12-hour clock format and specify the start and end as (MM/dd/yyyy hh:mm am/pm)\n"+
                "4. provide -search followed  by <customer>, <start> and <end> to get dates between dates start and end\n"+
                "5. just providing customer name prints entire phonebill of that customer of it exists.(this must be follwed by -host and -port options).";
        System.out.println(readme);
        System.exit(0);
    }
}