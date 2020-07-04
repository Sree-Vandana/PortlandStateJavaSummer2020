package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

  @Test
  public void ifOneArgIsREADMEPrintFileElseExitWithZero(){
      MainMethodResult result = invokeMain(Project1.class, "sree");
      if(! result.getTextWrittenToStandardOut().equals("This is a README file!")){
          assertThat(result.getExitCode(), equalTo(0));
      }
  }

  @Test
  public void passAnyLengthArgumentAndIfREADMEAtOneOrTwoThenPrintfile(){
      //MainMethodResult result = invokeMain(Project1.class, "-README", "-print");
      //MainMethodResult result = invokeMain(Project1.class,"-print","-README");
      //MainMethodResult result = invokeMain(Project1.class,"-print","-README","sree","123-123-1234");
      MainMethodResult result = invokeMain(Project1.class,"-print","sree","123-123-1234","-README");
      assertThat(result.getExitCode(), equalTo(0));
      //assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }

  @Ignore
  @Test
  public void passingSevenCommandLineArguments() {
    MainMethodResult result = invokeMain(Project1.class, "sree", "123-456-7890", "111-222-3333", "1/15/2020", "19:39", "01/2/2020", "1:03");
    assertThat(result.getExitCode(), equalTo(1));
    //assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
      MainMethodResult r = invokeMain();
  }



  @Ignore
  @Test
    public void passingArgsAndPrinting(){
      MainMethodResult result = invokeMain(Project1.class,"sree", "111-222-3333", "000-999-8888", "1/15/2020 19:39", "1/15/2020 20:39");
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }



}