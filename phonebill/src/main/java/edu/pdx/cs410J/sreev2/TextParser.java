package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;

public class TextParser implements PhoneBillParser<PhoneBill> {

    private static String path;

    public TextParser(String fileName) throws IOException {
        super();
        path = new String(createPath(fileName));
    }

    private String createPath(String fileName) throws IOException {
        if (fileName.matches("^.+?\\..*?") && !fileName.matches("^.+?\\.txt")) {
            throw new IllegalFileNameException("File must only have .txt extension or no extention\n"
                    + "Correct format = "+ "fileName or fileName.txt\n"+"Relative paths are accepted as well");
        }
        return (fileName.matches("^.+?\\.txt$") ? fileName : fileName + ".txt");
    }

    @Override
    public PhoneBill parse() throws ParserException {
        PhoneBill parsedPhoneBill = null;
        PhoneBill parsedPhoneBillname =new PhoneBill();
        String customerName = parsedPhoneBillname.getCustomer();
        try {
            fileHasSameCustomerName(customerName);
        } catch (IOException e) {
            throw new InvalidParameterException("Error occured while trying to compare customer name from console and from file");
        }

        final List<String> listOfPhoneCalls = new ArrayList<>();
        BufferedReader filedata = null;
        String strCurrentLine = null;

        File file = new File(path);
        if(!file.exists() || file.length() == 0){
            return parsedPhoneBill;
        }
        try {
            filedata = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new ParserException("Error while trying to read the file into BufferReader Object");
        }
        try{
            while((strCurrentLine = filedata.readLine()) != null){
                listOfPhoneCalls.add(strCurrentLine);
            }
        }catch (Exception e){
            throw new ParserException("Error while reading the contents of the file");
        }

        parsedPhoneBill = new PhoneBill(listOfPhoneCalls.get(0));
        for(String readPhoneCallInfo: listOfPhoneCalls){
            String[] args = readPhoneCallInfo.split(",");
            parsedPhoneBill.addPhoneCall(new PhoneCall(args, 1));
            }
        return parsedPhoneBill;
    }

    private void fileHasSameCustomerName(String customer) throws IOException{
        BufferedReader read = new BufferedReader(new FileReader(path));
        String customerName = read.readLine();
        if (customerName == null && customer == null)
            throw new InvalidParameterException("No customer name in Phone Bill");
        else if(customerName != null && !customerName.equals(customer))
            throw new InvalidParameterException("Given Customer name does not match with the name in Phone Bill");
        read.close();
    }
}
