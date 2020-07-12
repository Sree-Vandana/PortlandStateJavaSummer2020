package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class TextDumper implements PhoneBillDumper<PhoneBill>{

    private static String path;

    public TextDumper(String fileName) throws IOException{
        super();
        path = new String(pathGenerator(fileName));
       // createFileUsingPath(path);
    }



    private String pathGenerator(String fileName) throws IOException{
        if (fileName.matches("^.+?\\..*?") && !fileName.matches("^.+?\\.txt")) {
            throw new IllegalFileNameException("File must only have .txt extension or no extention\n"
                    + "Correct format = "+ "fileName or fileName.txt");
        }
        return (fileName.matches("^.+?\\.txt$") ? fileName : fileName + ".txt");
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {

    }
}
