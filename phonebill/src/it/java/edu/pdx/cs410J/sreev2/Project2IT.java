package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.IllegalFormatException;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */

    @Test
    public void NoCommandLineArgumentsAndOptions() {
        MainMethodResult result = invokeMain(Project2.class);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * test to check what happens when README switch is given as 1st or second argument
     * */
    @Test
    public void ifREADMEGivenAsAnOptionAtOneOrTwoPrintFileAndExit(){
        MainMethodResult result = invokeMain("-print", "-README", "-1SreeV2", "123-123-1234", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }

    /**
     * when print option is given along with too few arguments raises error.
     * */
    @Test
    public void ifPrintOptionIsGivenAllArgsNotGiven(){
        MainMethodResult result = invokeMain(Project2.class, "-print","-1SreeV2", "123-123-1234" );
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments"));
    }

    /**
     * when print option is given a along with all required arguments in valid format
     * */
    @Test
    public void ifGiveAllArgsValid(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-123-1234", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * the Caller phone number is in invalid format raises an error
     * */
    @Test
    public void isValidateFirstPhoneNumber(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-45-7890", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CallerNumber you entered is not in correct format"));
    }

    /**
     * the calle phone number is in invalid format raises an error
     * */
    @Test
    public void isValidateSecondPhoneNumber(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-456-7890", "1-222-333","1/15/2020", "19:39", "1/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CalleeNumber you entered is not in correct format"));
    }

    /**
     * the start date is in invalid format raises an error
     * */
    @Test
    public void isValidFirstDate(){
        // wrong Formats: 13/11/2020; 12/32/2020
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","12/32/2020", "19:39", "1/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The StartDate you entered, is in wrong format"));
    }

    /**
     * the end date is in invalid format raises an error
     * */
    @Test
    public void isValidSecondDate(){
        // wrong Formats: 13/11/2020; 12/32/2020
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "19:39", "13/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndDate you entered, is in wrong format"));
    }

    /**
     * the start time is in invalid format raises an error
     * */
    @Test
    public void isValidFirstTime(){
        // wrong fromat: 24:39; 2:60
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:60", "12/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The StartTime you entered, is in wrong format"));
    }

    /**
     * the end date is in invalid format raises an error
     * */
    @Test
    public void isValidSecondTime(){
        // wrong fromat: 24:39; 2:60
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "ABC-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndTime you entered, is in wrong format"));
    }

    @Test
    public void isValidSecondTimeIftextfileOptionIsgiven(){
        MainMethodResult result = invokeMain(Project2.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndTime you entered, is in wrong format"));

    }

    /**
     * when no options are given and too many arguments given raises an error
     * */
    @Test
    public void moreNumberOfArgumentsEnteredWithoutOptions(){
        MainMethodResult result = invokeMain(Project2.class, "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any valid options and the number of arguments entered are too many"));
    }

    /**
     * when no options are given and too few arguments given raises an error
     * with only one error statement.
     * */
    @Test
    public void noOptionsWithFewDataArgsmustPrintOnlyNoOptionsProvidedStatement(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "123-123-4");
        assertThat(result.getTextWrittenToStandardOut(), not(containsString("Not Sufficient number of arguments")));
    }

    /**
     * when no options are given and too few arguments raises an error
     * */
    @Test
    public void noOptionButTooFewArgumentsPrintsError(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "123-123-1234");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any valid options and the number of arguments entered are incomplete"));
    }

    /**
     * when no options are given but correct number of required arguments are given prints nothing.
     * */
    @Test
    public void noOptionsAndCorrectNumberOfArgumentsPrintsNothing(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06");
        assertThat(result.getTextWrittenToStandardError(), not(containsString("You did not enter any options and the number of arguments entered are incomplete")));
    }

    /**
     * when no options given checking for errors for edge cases with 8 and 9 args
     * */
    @Test
    public void noOptionButTooManyArgumentsEightAndNineraiseError(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06", "sree");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any options and the number of arguments entered are too many"));
    }


    @Test
    public void callerPhoneNumberHasNonIntegerValuesRaiseError(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "ABC-222-3333", "123-123-8888", "1/31/2020", "2:06", "12/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CallerNumber you entered is not in correct format"));
    }

    @Test
    public void calleePhoneNumberHasNonIntegerValuesRaiseError(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "123-222-3333", "123-1A3-8888", "1/31/2020", "2:06", "12/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CalleeNumber you entered is not in correct format"));
    }

    @Test
    public void startTimeIsMalformattedRaiseError(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "123-222-3333", "123-123-8888", "1/31/2020", "2:XX", "12/15/2020", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The StartTime you entered, is in wrong format"));
    }

    @Test
    public void endDateIsMalformattedRaiseError(){
        MainMethodResult result = invokeMain(Project2.class, "sree", "123-222-3333", "123-123-8888", "1/31/2020", "2:00", "01/04/20/1", "20:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndDate you entered, is in wrong format"));
    }

    @Test
    public void givenOnlyTextFileOptionWithoutFileNameWithPrint(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "-print", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:06");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));

    }

    @Test
    public void givenOnlyTextFileOptionWithoutFileName(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:06");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));

    }

    @Test
    public void givenTextFileFileNameOptionwithtooLittelArguments(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "sree/sreefile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));
    }

    @Test
    public void givenTextFileFileNameOptionwithtooManyArguments(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "sree/sreefile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:00", "sree");
        assertThat(result.getTextWrittenToStandardError(), containsString("The Arguments required are 7. seems like you have entered more than required Arguments"));
    }

    @Test
    public void givenTextFileFileNameOptionalongwithPrintOutputsPhoneCall(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "sree/sreefile", "-print", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:00");
        assertThat(result.getTextWrittenToStandardOut(), containsString("sree's latest Phone Call Information"));
    }

    @Test
    public void givingOnlytextFileOptionPrintsErrorStatement(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "fileName");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));
    }

    @Test
    public void givingOnlytextFileOptionWithArgumentsDumpThemIntoFile(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "sree/sreefile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:00");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The given phone call is successfully dumped into the file."));
    }

    @Test
    public void givingPrintAlingWithTextFileIsteadOfFileNameMustRaiseError(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile","-print", "sree/sreefile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:00");
        assertThat(result.getTextWrittenToStandardError(), containsString("The \"FileName\" must be given only after -textFile Option"));
    }

    @Test
    public void optionsMustBegivenBeforeArguments(){
        MainMethodResult result = invokeMain(Project2.class, "-textFile", "sree/sreefile", "sree", "-print", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:00");
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

}
