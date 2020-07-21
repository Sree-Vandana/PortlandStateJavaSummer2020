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
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */

    @Test
    public void NoCommandLineArgumentsAndOptions() {
        MainMethodResult result = invokeMain(Project3.class);
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
        MainMethodResult result = invokeMain(Project3.class, "-print","-1SreeV2", "123-123-1234" );
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments"));
    }

    /**
     * when print option is given a along with all required arguments in valid format
     * */
    @Test
    public void ifGiveAllArgsValid(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-123-1234", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * the Caller phone number is in invalid format raises an error
     * */
    @Test
    public void isValidateFirstPhoneNumber(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-45-7890", "111-222-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CallerNumber you entered is not in correct format"));
    }

    /**
     * the calle phone number is in invalid format raises an error
     * */
    @Test
    public void isValidateSecondPhoneNumber(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-456-7890", "111-A22-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CalleeNumber you entered is not in correct format"));
    }

    /**
     * the start date is in invalid format raises an error
     * */
    @Test
    public void isValidFirstDate(){
        // wrong Formats: 13/11/2020; 12/32/2020
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333", "1/15/20/0", "12:39", "AM", "1/15/2020", "11:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The StartDate you entered, is in wrong format"));
    }

    /**
     * the end date is in invalid format raises an error
     * */
    @Test
    public void isValidSecondDate(){
        // wrong Formats: 13/11/2020; 12/32/2020
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/15/2020", "12:39", "AM", "1/85/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndDate you entered, is in wrong format"));
    }

    /**
     * the start time is in invalid format raises an error
     * */
    @Test
    public void isValidFirstTime(){

        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/15/2020", "13:39", "AM", "1/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The StartTime you entered, is in wrong format"));
    }

    /**
     * the end date is in invalid format raises an error
     * */
    @Test
    public void isValidSecondTime(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/15/2020", "12:39", "AM", "1/15/2020", "11:59", "zM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndTime you entered, is in wrong format"));
    }

    @Test
    public void isValidSecondTimeIftextfileOptionIsgiven(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sreev2/sv", "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/15/2020", "12:39", "am", "1/15/2020", "20:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndTime you entered, is in wrong format"));

    }

    /**
     * when no options are given and too many arguments given raises an error
     * */
    @Test
    public void moreNumberOfArgumentsEnteredWithoutOptions(){
        MainMethodResult result = invokeMain(Project3.class, "-1SreeV2?", "123-456-7890", "111-A22-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM", "sree");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any valid options and the number of arguments entered are too many"));
    }

    /**
     * when no options are given and too few arguments given raises an error
     * with only one error statement.
     * */
    @Test
    public void noOptionsWithFewDataArgsmustPrintOnlyNoOptionsProvidedStatement(){
        MainMethodResult result = invokeMain(Project3.class, "sree", "123-123-4");
        assertThat(result.getTextWrittenToStandardOut(), not(containsString("Not Sufficient number of arguments")));
    }

    /**
     * when no options are given and too few arguments raises an error
     * */
    @Test
    public void noOptionButTooFewArgumentsPrintsError(){
        MainMethodResult result = invokeMain(Project3.class, "sree", "123-123-1234");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any valid options and the number of arguments entered are incomplete"));
    }

    /**
     * when no options are given but correct number of required arguments are given prints nothing.
     * */
    @Test
    public void noOptionsAndCorrectNumberOfArgumentsPrintsNothing(){
        MainMethodResult result = invokeMain(Project3.class, "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06");
        assertThat(result.getTextWrittenToStandardError(), not(containsString("You did not enter any options and the number of arguments entered are incomplete")));
    }

    /**
     * when no options given checking for errors for edge cases with 8 and 9 args
     * */
    @Test
    public void noOptionButTooManyArgumentsEightAndNineraiseError(){
        MainMethodResult result = invokeMain(Project3.class, "-1SreeV2?", "123-456-7890", "111-A22-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM", "sree");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any valid options and the number of arguments entered are too many"));
    }


    @Test
    public void callerPhoneNumberHasNonIntegerValuesRaiseError(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-1SreeV2?", "ABC-456-7890", "111-222-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CallerNumber you entered is not in correct format"));
    }

    @Test
    public void calleePhoneNumberHasNonIntegerValuesRaiseError(){
        MainMethodResult result = invokeMain(Project3.class, "-1SreeV2?", "123-456-7890", "111-A22-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The CalleeNumber you entered is not in correct format"));
    }

    @Test
    public void startTimeIsMalformattedRaiseError(){
        MainMethodResult result = invokeMain(Project3.class, "-1SreeV2?", "123-456-7890", "111-222-3333","1/15/2020", "12:xx", "AM", "1/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The StartTime you entered, is in wrong format"));
    }

    @Test
    public void endDateIsMalformattedRaiseError(){
        MainMethodResult result = invokeMain(Project3.class, "-1SreeV2?", "123-456-7890", "111-222-3333","1/15/2020", "12:39", "AM", "1/15/b020", "2:09", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The EndDate you entered, is in wrong format"));
    }

    @Test
    public void givenOnlyTextFileOptionWithoutFileNameWithPrint(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "-print", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:06");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));

    }

    @Test
    public void givenOnlyTextFileOptionWithoutFileName(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:06");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));

    }

    @Test
    public void givenTextFileFileNameOptionwithtooLittelArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sree/sreefile", "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));
    }

    @Test
    public void givenTextFileFileNameOptionwithtooManyArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sree/sreefile", "-1SreeV2?", "123-456-7890", "111-A22-3333","1/15/2020", "12:39", "AM", "1/15/2020", "2:39", "PM","qwerty");
        assertThat(result.getTextWrittenToStandardError(), containsString("The Arguments required are 9. seems like you have entered more than required Arguments"));
    }


    @Test
    public void givenTextFileFileNameOptionalongwithPrintOutputsPhoneCall(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sree/sreefile", "-print", "sree", "123-456-7890", "111-222-3333","01/15/2020", "12:39", "AM", "01/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("sree's latest Phone Call Information"));
    }

    @Test
    public void givingOnlytextFileOptionPrintsErrorStatement(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "fileName");
        assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments provided"));
    }

    @Test
    public void givingOnlytextFileOptionWithArgumentsDumpThemIntoFile(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sree/sreefile", "sree", "111-222-3333", "000-999-8888", "01/15/2020", "11:39","AM", "02/15/2020", "12:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The given phone call is successfully dumped into the textFile."));
    }

    @Test
    public void givingPrintAlingWithTextFileIsteadOfFileNameMustRaiseError(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","-print", "sree/sreefile", "-1SreeV2?", "123-456-7890", "111-222-3333","01/15/2020", "12:39", "AM", "01/15/2020", "2:39", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The \"FileName\" must be given only after -textFile Option"));
    }

    @Test
    public void optionsMustBegivenBeforeArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "sree/sreefile", "sree", "-print", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "2/15/2020", "20:00");
        assertThat(result.getTextWrittenToStandardError(), containsString("SomeThing is out of order"));
    }

    @Test
    public void givingPrettyOptionWithoutArgumentsRaisesError(){
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "prettyFile");
        assertThat(result.getTextWrittenToStandardError(),containsString("Not Sufficient number of arguments provided"));
    }

    @Test
    public void giveningPrettyOptionWithFileNameDumpsTheLatestPhoneCallIntoPrettyFile(){
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "prettyTest/prettyFile", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "11:39","AM", "02/14/2020", "12:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(),containsString("Phone calls successfully entered into pretty file"));
    }

    @Test
    public void givingPrettyAndTextFileOptionAtATimeWithFileNAmes(){
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "pretty/prettyFile", "-textFile", "sree/sreefile", "sree", "211-222-3333", "000-999-8888", "01/15/2020", "9:38","AM", "01/15/2020", "9:40", "AM");
        assertThat(result.getTextWrittenToStandardOut(),containsString("Phone calls successfully entered into pretty file"));
    }

    @Test
    public void givenOnlyPrintOption(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "11:39","AM", "02/14/2020", "12:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("sree's Phone Call Information"));
    }

    @Test
    public void givenPrittyAndPrintWithFileName(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-pretty", "pretty/prettyFile.txt", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone calls successfully entered into pretty file"));
    }

    @Test
    public void givenPrittyAndPrintWithHypin(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-pretty", "-", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Customer: sree"));
    }

    @Test
    public void notGivingFileNameOrHyphinAfterPrettyRaisesError(){
        MainMethodResult result = invokeMain(Project3.class, "-pretty","-print", "-", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The \"FileName\" or \"-\" must be given after -pretty Option"));
    }

    @Test
    public void notGivingFileNameOrHyphinAfterPrettyOrTextFileRaisesError(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","-pretty","fileName.txt","pretty/prettyFile", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Something is out of order"));
    }

    @Test
    public void AllThreeOptionsAreGivenWithPrettyTextFile(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","fileName.txt","-pretty","pretty/prettyFile","-print", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The given phone call is successfully dumped into the textFile"));
    }

    @Test
    public void AllThreeOptionsAreGivenWithPrettyHyphen(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","fileName.txt","-pretty","-","-print", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The given phone call is successfully dumped into the textFile"));
    }

    @Test
    public void prityPrintGivenOutOfOrder(){
        MainMethodResult result = invokeMain(Project3.class, "-pretty","-print","-", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("The \"FileName\" or \"-\" must be given after -pretty Option"));
    }

    @Test
    public void givingSameFileNameForTextFileAndPretty(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","fileName.txt","-pretty","fileName.txt", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("please provide different file name/path for -pretty option and -textFile option to avoid unwanted behaviour."));
    }

    @Test
    public void AllThreeOptionsGivenSameFileNameForTextFileAndPretty(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","fileName.txt","-pretty","fileName.txt","-print", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("please provide different file name/path for -pretty option and -textFile option to avoid unwanted behaviour."));
    }

    @Test
    public void greaterThanFifteenArgumentsWithOptions(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile","fileName.txt","-pretty","fileName.txt","-print", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM", "sree","asdf");
        assertThat(result.getTextWrittenToStandardError(), containsString("Seems like you have entered more than the required Arguments"));
    }

    @Test
    public void givingGreateThanFifteenArgumentsWithoutOptions(){
        MainMethodResult result = invokeMain(Project3.class, "sree", "111-222-3333", "000-999-8888", "01/14/2020", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any valid options and the number of arguments entered are too many"));
    }

    @Test
    public void givingNoOptionsWithAllCorrectArguments(){
        MainMethodResult result = invokeMain(Project3.class, "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:30", "AM");
        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    }

    @Test
    public void startDateAndtimeShouldNotBEAfterEndDateAndTime(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "sree", "111-222-3333", "000-999-8888", "01/14/2020", "12:09","AM", "01/14/2020", "12:09", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Start date and time can not be equals or after the end date and time of the phone call"));
    }


}
