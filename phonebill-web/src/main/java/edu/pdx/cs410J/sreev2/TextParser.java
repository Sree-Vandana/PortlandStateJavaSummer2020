package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;
import java.util.regex.Pattern;

public class TextParser implements PhoneBillParser<PhoneBill> {
    private final Reader reader;

    /**
     * argument path of <type>String</type>
     * hold sthe path or file name which need to be written
     * */

    /**
     *
     *          the name or path of the file in which data is written
     * @throws IOException
     * By calling this constructor, path of the file will be created with the given fileName.
     * */
    public TextParser(Reader reader) {
        this.reader = reader;
    }


    /**
     * @return parsedPhoneBill <class>PhoneBill</class>
     * @throws ParserException
     * This method reads the contents present in the file, that is written by <class>TextDumper</class>
     * and appends them to <class>PhoneBill</class>
     * */
/*
    @Override
    public PhoneBill parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.reader);
        try {
            String customer = br.readLine();

            PhoneBill bill = new PhoneBill(customer);

            while (br.ready()) {
                String caller = br.readLine();
                if (caller == null) {
                    break;
                }
                bill.addPhoneCall(new PhoneCall(caller));
            }

            return bill;

        } catch (IOException e) {
            throw new ParserException("While parsing", e);
        }
    }*/

    @Override
    public PhoneBill parse() throws ParserException {

        PhoneBill parsedPhoneBill = null;
        final List<String> listOfPhoneCalls = new ArrayList<>();
        BufferedReader br = new BufferedReader(this.reader);
        String strCurrentLine = null;

        try {
            /*File file = new File(path);
            if(!file.exists() || file.length() == 0)
                return null;*/
           // readerData = new BufferedReader(new FileReader(file));
            while((strCurrentLine = br.readLine()) != null){
                listOfPhoneCalls.add(strCurrentLine);
            }
            if(listOfPhoneCalls == null)
                return parsedPhoneBill;
        } catch (IOException e) {
            throw new ParserException("Error occured when trying to parse data");
        }
        int count = 0;
        for(String phoneCall: listOfPhoneCalls){
            if(phoneCall == listOfPhoneCalls.get(0))
                parsedPhoneBill = new PhoneBill(phoneCall);
            else{
                if(count != 0) {
                    String[] args = phoneCall.split("\\s|,");
                    parsedPhoneBill.addPhoneCall(new PhoneCall(args, 1));
                }
                count++;
            }
        }
        return parsedPhoneBill;
    }

/*
if (bill.getPhoneCalls().isEmpty())
    prettyPhoneCalls = "No Phone calls found between " + start  +" and " + end;
        else
    prettyPhoneCalls = "Phone Calls between dates "+ start + " and " + end +":\n" + prettyPrinter.getPrettyPhoneCalls(bill);
*/

}
