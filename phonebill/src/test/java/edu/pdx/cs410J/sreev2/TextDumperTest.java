package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextDumperTest {

    @Test(expected = IllegalFileNameException.class)
    public void createObjectOfTextDumberWithFileWithOtherExtension() throws IOException {
        TextDumper test1 = new TextDumper("TestFileName.txttt");
        TextDumper test2 = new TextDumper("TestFileName.java");
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
    public void sendingFileNameWithRelativePathAndWithoutExtentionMustNotRaiseError(){
        try {
            TextDumper test1 = new TextDumper("sreev2/TestFileName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendingFileNameWithRelativePathAndWithExtentionMustNotRaiseError(){
        try {
            TextDumper test1 = new TextDumper("sreev2/TestFileName.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfDataBeingWrittenInCorrectFormatWithoutRaisingErrors(){
        try {
            TextDumper test1 = new TextDumper("sreev2/TestFileName.txt");
            String[] args = {"-print","sreev2", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06"};
            test1.dump(new PhoneBill("sreev2", new PhoneCall(args)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
