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
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */

  @Test
  public void NoCommandLineArgumentsAndOptions() {
    MainMethodResult result = invokeMain(Project1.class);
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
      MainMethodResult result = invokeMain(Project1.class, "-print","-1SreeV2", "123-123-1234" );
      assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments, to perform -print function"));
  }

  /**
   * when print option is given a along with all required arguments in valid format
   * */
  @Test
    public void ifGiveAllArgsValid(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-123-1234", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
      assertThat(result.getExitCode(), equalTo(1));
  }

  /**
   * the Caller phone number is in invalid format raises an error
   * */
  @Test
  public void isValidateFirstPhoneNumber(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-45-7890", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("The CallerNumber you entered is not in correct format"));
  }

  /**
   * the calle phone number is in invalid format raises an error
   * */
  @Test
  public void isValidateSecondPhoneNumber(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-456-7890", "1-222-333","1/15/2020", "19:39", "1/15/2020", "20:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("The CalleeNumber you entered is not in correct format"));
  }

  /**
   * the start date is in invalid format raises an error
   * */
  @Test
  public void isValidFirstDate(){
    // wrong Formats: 13/11/2020; 12/32/2020
    MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","12/32/2020", "19:39", "1/15/2020", "20:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("The StartDate you entered, is in wrong format"));
  }

  /**
   * the end date is in invalid format raises an error
   * */
  @Test
  public void isValidSecondDate(){
    // wrong Formats: 13/11/2020; 12/32/2020
    MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "19:39", "13/15/2020", "20:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("The EndDate you entered, is in wrong format"));
  }

  /**
   * the start time is in invalid format raises an error
   * */
  @Test
  public void isValidFirstTime(){
    // wrong fromat: 24:39; 2:60
    MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:60", "12/15/2020", "20:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("The StartTime you entered, is in wrong format"));
  }

  /**
   * the end date is in invalid format raises an error
   * */
  @Test
  public void isValidSecondTime(){
    // wrong fromat: 24:39; 2:60
    MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("The EndTime you entered, is in wrong format"));
  }

  /**
   * when no options are given and too many arguments given raises an error
   * */
  @Test
  public void moreNumberOfArgumentsEnteredWithoutOptions(){
    MainMethodResult result = invokeMain(Project1.class, "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39", "-1SreeV2?", "123-456-7890", "111-222-3333","1/31/2020", "2:06", "12/15/2020", "24:39");
    assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any options and the number of arguments entered are too many"));
  }

  /**
   * when no options are given and too few arguments given raises an error
   * with only one error statement.
   * */
  @Test
  public void noOptionsWithFewDataArgsmustPrintOnlyNoOptionsProvidedStatement(){
    MainMethodResult result = invokeMain(Project1.class, "sree", "123-123-4");
    assertThat(result.getTextWrittenToStandardOut(), not(containsString("Not Sufficient number of arguments, to perform -print function")));
  }

  /**
   * when no options are given and too few arguments raises an error
   * */
  @Test
  public void noOptionButTooFewArgumentsPrintsError(){
    MainMethodResult result = invokeMain(Project1.class, "sree", "123-123-1234");
    assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any options and the number of arguments entered are incomplete"));
  }

  /**
   * when no options are given but correct number of required arguments are given prints nothing.
   * */
  @Test
  public void noOptionsAndCorrectNumberOfArgumentsPrintsNothing(){
    MainMethodResult result = invokeMain(Project1.class, "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06");
    assertThat(result.getTextWrittenToStandardError(), not(containsString("You did not enter any options and the number of arguments entered are incomplete")));
  }

  /**
   * when no options given checking for errors for edge cases with 8 and 9 args
   * */
  @Test
  public void noOptionButTooManyArgumentsEightAndNineraiseError(){
    MainMethodResult result = invokeMain(Project1.class, "sree", "111-222-3333", "000-999-8888", "1/15/2020", "19:39", "1/15/2020", "20:06", "sree");
    assertThat(result.getTextWrittenToStandardError(), containsString("You did not enter any options and the number of arguments entered are too many"));
  }

}