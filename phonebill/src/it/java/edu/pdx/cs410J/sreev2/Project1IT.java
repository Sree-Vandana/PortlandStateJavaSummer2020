package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.IllegalFormatException;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
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
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
   * If its -README exits code is 1 else 0
   * */
  @Test
  public void testOneCommandLineArgumentsIfREADMEorNot() {
    MainMethodResult result = invokeMain(Project1.class, "-README");
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void ifOneArgIsREADMEPrintFile(){
      MainMethodResult result = invokeMain(Project1.class, "-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }

  @Test//(expected = IllegalArgumentException.class)
  public void ifOneArgIsNeitherREADMENorPrintThroeIllegalArgExceptionAndExitWithZero(){
      MainMethodResult result = invokeMain(Project1.class, "sree");
      assertThat(result.getExitCode(), equalTo(0));
      }


  @Test
  public void passAnyLengthArgumentAndIfREADMEAtOneOrTwoThenPrintfile(){
      MainMethodResult result = invokeMain(Project1.class, "-README", "-print");
      //MainMethodResult result = invokeMain(Project1.class,"-print","-README");
      //MainMethodResult result = invokeMain(Project1.class,"-print","-README","sree","123-123-1234");
      //MainMethodResult result = invokeMain(Project1.class,"-print","sree","123-123-1234","-README");
      assertThat(result.getExitCode(), equalTo(1));
      //assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }
    @Ignore
  @Test//(expected = IllegalNumberOfArgumentException.class)
  public void onlyPrintOptionIsSentWithoutArgumemnts(){
      MainMethodResult result = invokeMain(Project1.class,"-print");
      assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  public void validNameIsAStringFormat(){
    //can have -, numbers, _, " ",., alphabets
    // should not contain "special char"
        MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2", "123-123-1234", "123-123-1234","1/15/2020", "19:39", "1/15/2020", "19:39" );
        assertThat(result.getExitCode(),equalTo(1));
      //assertThat(result.getTextWrittenToStandardOut(), containsString("sree"));
  }

}