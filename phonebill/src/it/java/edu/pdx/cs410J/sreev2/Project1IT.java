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
  public void NoCommandLineArgumentsAndOptions() {
    MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
    public void ifREADMEGivenAsAnOptionAtOneOrTwoPrintFileAndExit(){
      MainMethodResult result = invokeMain("-print", "-README", "-1SreeV2", "123-123-1234", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }

  @Test
    public void ifPrintOptionIsGivenAllArgsNotGiven(){
      MainMethodResult result = invokeMain(Project1.class, "-print","-1SreeV2", "123-123-1234" );
      assertThat(result.getTextWrittenToStandardError(), containsString("Not Sufficient number of arguments, to perform -print function"));
  }
    @Ignore
  @Test
    public void ifGivenAllArgsValidateNameFormat(){
      MainMethodResult result = invokeMain(Project1.class, "-print", "-1SreeV2", "123-123-1234", "111-222-3333","1/15/2020", "19:39", "1/15/2020", "20:39");
  }

}