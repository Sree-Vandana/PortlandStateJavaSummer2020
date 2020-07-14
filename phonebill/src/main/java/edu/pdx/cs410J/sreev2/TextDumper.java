package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class TextDumper implements PhoneBillDumper<PhoneBill>{

    /**
     * argument path of <type>String</type>
     * hold sthe path or file name which need to be written
     * */
    private static String path;

    /**
     * @param fileName
     *          the name or path of the file in which data is written
     * @throws IOException
     * By calling this constructor, the file will be created with the given fileName.
     * */
    public TextDumper(String fileName) throws IOException{
        super();
        path = new String(createPath(fileName));
        createFileUsingPath(path);
    }

    /**
     * @param phoneBill <class>PhoneBill</class>
     *                  The PhoneBill object which holds the information of phonecall that need to be written in a file
     * @throws IOException
     * This method, enters the phone call information in my defined format into a file.
     * */
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

    /**
     * @param customer <type>String</type>
     *                 Customer name entered in command line arguments
     * @throws IOException
     * @throws InvalidParameterException
     * This method checks if the file has the same customer name as the name entered in command line arguments.
     * */
    private void fileHasSameCustomerName(String customer) throws IOException{
        BufferedReader read = new BufferedReader(new FileReader(path));
        String customerName = read.readLine();
        if (customerName == null && customer == null)
            throw new InvalidParameterException("No customer name in Phone Bill");
        else if(customerName != null && !customerName.equals(customer))
            throw new InvalidParameterException("Given Customer name does not match with the name in Phone Bill");
        read.close();
    }

    /**
     * @param path <type>String</type>
     *             file path or file name
     * @throws IOException
     * @throws InvalidParameterException
     * this method creates the file at the location specified by <argument>path</argument>
     * */
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

    /**
     * @param fileName <type>String</type>
     *                 file path or file name
     * @return path <type>String</type>
     * This method checks if the file name has extentions othe rthan .txt
     * */
    private String createPath(String fileName) throws IOException{
        if (fileName.matches("^.+?\\..*?") && !fileName.matches("^.+?\\.txt")) {
            throw new IllegalFileNameException("File must only have .txt extension or no extention\n"
                    + "Correct format = "+ "fileName or fileName.txt\n"+"Relative paths are accepted as well");
        }
        return (fileName.matches("^.+?\\.txt$") ? fileName : fileName + ".txt");
    }

}
