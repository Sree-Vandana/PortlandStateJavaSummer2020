package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

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

    @Test(expected = InvalidParameterException.class)
    public void fileNotHavingSameCustomerNameThrowsError() throws IOException, ParserException {
        TextParser test1 = new TextParser("sreev2/TestFileName.txt");
        try {
            TextDumper test = new TextDumper("sreev2/Testpat.txt");
            String[] args = {"-print","pat", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06"};
            test.dump(new PhoneBill("pat", new PhoneCall(args)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        test1.parse();
    }

    /*@Test
    public void testingTheFormatOFParsedPhoneBillObject() throws IOException, ParserException {
        TextParser test1 = new TextParser("sreev2/TestFileName.txt");
        TextDumper test = new TextDumper("sreev2/TestFileName.txt");
        String[] args = {"-print","sreev2", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06"};
        test.dump(new PhoneBill("sreev2", new PhoneCall(args)));
        PhoneBill pb = test1.parse();
        assertThat(pb.getPhoneCalls(), equalTo("sreev2"));
    }*/
}
