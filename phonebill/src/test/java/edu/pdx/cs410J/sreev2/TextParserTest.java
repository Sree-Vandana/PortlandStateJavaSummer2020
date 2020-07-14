package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class TextParserTest {
    @Test
    public void sendingFileNameWithoutExtentionMustNotRaiseError(){
        try {
            TextParser test1 = new TextParser("TestFileName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendingFileNameWithRelativePathAndWithoutExtentionMustNotRaiseError(){
        try {
            TextParser test1 = new TextParser("sreev2/TestFileName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendingFileNameWithRelativePathAndWithExtentionMustNotRaiseError(){
        try {
            TextParser test1 = new TextParser("sreev2/TestFileName.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalFileNameException.class)
    public void sendingFileNameWithAnotherExtentionOtherThanTXTMustNotRaiseError() throws IOException{
        TextParser test1 = new TextParser("sreev2/TestFileName.txtt");
    }

}
