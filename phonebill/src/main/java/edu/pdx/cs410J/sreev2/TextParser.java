package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;
import java.util.regex.Pattern;

public class TextParser implements PhoneBillParser<PhoneBill> {

    /**
     * argument path of <type>String</type>
     * hold sthe path or file name which need to be written
     * */
    private static String path;

    /**
     * @param fileName
     *          the name or path of the file in which data is written
     * @throws IOException
     * By calling this constructor, path of the file will be created with the given fileName.
     * */
    public TextParser(String fileName) throws IOException {
        super();
        path = new String(createPath(fileName));
    }

    /**
     * @return parsedPhoneBill <class>PhoneBill</class>
     * @throws ParserException
     * This method reads the contents present in the file, that is written by <class>TextDumper</class>
     * and appends them to <class>PhoneBill</class>
     * */
    @Override
    public PhoneBill parse() throws ParserException {

        PhoneBill parsedPhoneBill = null;
        final List<String> listOfPhoneCalls = new ArrayList<>();
        BufferedReader fileData = null;
        String strCurrentLine = null;

        try {
            File file = new File(path);
            if(!file.exists() || file.length() == 0)
                return null;
            fileData = new BufferedReader(new FileReader(file));
            while((strCurrentLine = fileData.readLine()) != null){
                listOfPhoneCalls.add(strCurrentLine);
            }
            if(listOfPhoneCalls == null)
                return parsedPhoneBill;
        } catch (IOException e) {
            throw new ParserException("Error occured when trying to read from file");
        }
        int count = 0;
        for(String phoneCall: listOfPhoneCalls){
            if(phoneCall == listOfPhoneCalls.get(0))
                parsedPhoneBill = new PhoneBill(phoneCall);
            else{
                if(count != 0) {
                    String[] args = phoneCall.split("\\s|,");
                    if(!valiadteArgsFormats(args, 0,1,2,3,4,5,6,7))
                        throw new ParserException("The Data in the File is malformatted. phonecall information number " + count + " is modified");
                    parsedPhoneBill.addPhoneCall(new PhoneCall(args, 1));
                }
                count++;
            }
        }
        return parsedPhoneBill;
    }

    /**
     * @param fileName <type>String</type>
     *                 file path or file name
     * @return path <type>String</type>
     * This method checks if the file name has extentions othe rthan .txt
     * */
    private String createPath(String fileName) throws IOException {
        if (fileName.matches("^.+?\\..*?") && !fileName.matches("^.+?\\.txt")) {
            throw new IllegalFileNameException("File must only have .txt extension or no extention\n"
                    + "Correct format = "+ "fileName or fileName.txt\n"+"Relative paths are accepted as well");
        }
        return (fileName.matches("^.+?\\.txt$") ? fileName : fileName + ".txt");
    }

    private static Boolean valiadteArgsFormats(String[] args, int... index) {

        boolean phno1 = isValidatePhoneNumber(args[index[0]]);
        if(!phno1){
            System.err.println("The CallerNumber read from file is not in correct format\n");
        }

        boolean phno2 = isValidatePhoneNumber(args[index[1]]);
        if(!phno2){
            System.err.println("The CalleeNumber read from file is not in correct format\n");
        }

        boolean date1 = isValidateDate(args[index[2]]);
        if(!date1){
            System.err.println("The StartDate read from file, is in wrong format\n");
        }

        boolean date2 = isValidateDate(args[index[5]]);
        if(!date2){
            System.err.println("The EndDate read from file, is in wrong format\n");
        }

        boolean time1 = isValidateTime(args[index[3]] + " " + args[index[4]]);
        if(!time1){
            System.err.println("The StartTime read from file, is in wrong format\n");
        }

        boolean time2 = isValidateTime(args[index[6]] + " " + args[index[7]]);
        if(!time2){
            System.err.println("The EndTime read from file, is in wrong format\n");
        }

        if(phno1 & phno2 & date1 & date2 & time1 & time2){
            return true;
        }
        else{
            //System.err.println("The Data in the File is malformatted\n");
            return false;
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
        return Pattern.matches(regexTime, time);
    }

}
