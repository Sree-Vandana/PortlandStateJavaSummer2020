package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class TextDumper implements PhoneBillDumper<PhoneBill>{

    private static String path;

    public TextDumper(String fileName) throws IOException{
        super();
        path = new String(createPath(fileName));
        createFileUsingPath(path);
    }

    private void createFileUsingPath(String path) throws IOException, InvalidParameterException {
        File file = new File(path);
        File mkdir = null;
        if(path.contains("/")) {
            var directory = Arrays.asList(path.split("/" + file.getName()));
            mkdir = new File(directory.get(0));
        }
        if (mkdir != null && !mkdir.exists())
            mkdir.mkdirs();
        if (!file.exists())
            file.createNewFile();
        else if(file.length() == 0) {
            throw new InvalidParameterException("Phone bill does not have customer name");
        }
    }

    private String createPath(String fileName) throws IOException{
        if (fileName.matches("^.+?\\..*?") && !fileName.matches("^.+?\\.txt")) {
            throw new IllegalFileNameException("File must only have .txt extension or no extention\n"
                    + "Correct format = "+ "fileName or fileName.txt\n"+"Relative paths are accepted as well");
        }
        return (fileName.matches("^.+?\\.txt$") ? fileName : fileName + ".txt");
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        fileHasSameCustomerName(phoneBill.getCustomer());
        String separator = ",";
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file, true);
        var phoneCalls = phoneBill.getPhoneCalls();
        if(file.length() == 0){
            fileWriter.append(phoneBill.getCustomer()+"\n"
                    + "Caller Number"+ separator + "Callee Number"
                    + separator + "Start Date and Time"
                    + separator + "End Date and Time");
        }else {
            fileWriter.append("");
        }
        for(PhoneCall phoneCall : phoneCalls){
            fileWriter.append("\n" + phoneCall.getCaller()+ separator + phoneCall.getCallee()
                    + separator + phoneCall.getStartTimeString()
                    + separator + phoneCall.getEndTimeString());
        }
        fileWriter.flush();
        fileWriter.close();
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
