package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;

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
                    String[] args = phoneCall.split(",");
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

}
