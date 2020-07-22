package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {

    /**
     * path of type <code>String</code>
     * */
    private static String path;

    /**
     * default constructor, generates a path and creates a file
     * @param fileName
     *        fileName.txt stores the pretty print output
     * */
    PrettyPrinter(String fileName) throws IOException{
        super();
        path = new String(createPath(fileName));
        createFileUsingPath(path);
        if(fileName.equals("-")){
            File f = new File(fileName+".txt");
            f.deleteOnExit();
        }
    }

    /**
     * writes the pretty phone calls into pretty file
     * @param phoneBill
     *        of type <class>PhoneBill</class>
     * @throws IllegalArgumentException
     * */
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        if(!path.equals("-.txt")){
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(prettyPhoneBill(phoneBill));
            fileWriter.flush();
            fileWriter.close();
        }
        else {
            throw new IllegalFileNameException("The given file is not supported");
        }
    }

    /**
     * writes the phoneBill in pretty format onto console
     * @param phoneBill
     *        type <class>PhoneBill</class>
     * */
    final public void printOnStandardIO(PhoneBill phoneBill){
        System.out.println(prettyPhoneBill(phoneBill));
    }

    /**
     * takes the contents from PhoneCall collection and generates a string for each phonecall
     * @param phoneBill
     *        of type <class>PhoneBill</class>
     * @return String
     *         containing entire phoneBill
     * */
    private String prettyPhoneBill(PhoneBill phoneBill) {
        var size = phoneBill.getPhoneCalls().size();
        int counter = size;
        String pretty = "Customer: "+phoneBill.getCustomer()+"\nNumber of phone calls: "+size+"\n";
        pretty += "#     Caller Phone Number     Callee Phone Number     Call Started At      Call Ended At       Call Duration\n";
        pretty += "-------------------------------------------------------------------------------------------------------------\n";
        for (PhoneCall c : phoneBill.getPhoneCalls()) {
            pretty += String.format("%-8d %-23s %-19s %-19s %-18s %8d Minutes\n",size - --counter, c.getCaller(),c.getCallee(),
                    c.getPrettyDateTime(1), c.getPrettyDateTime(2), c.callDuration());
        }
        return pretty;
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
}
