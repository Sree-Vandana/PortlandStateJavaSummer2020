package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextDumperTest {

    @Test(expected = IllegalFileNameException.class)
    public void createObjectOfTextDumberWithFileWithOtherExtension() throws IOException {
        TextDumper test1 = new TextDumper("TestFileName.txttxt");
        TextDumper test2 = new TextDumper("TestFileName.pdf");
    }

    @Test
    public void sendingFileNameWithoutExtentionMustNotRaiseError(){
        try {
            TextDumper test1 = new TextDumper("TestFileName");
            test1.dump(new PhoneBill("pat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendingFileNameWithWithExtentionMustNotRaiseError(){
        try {
            TextDumper test1 = new TextDumper("TestFileName.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
