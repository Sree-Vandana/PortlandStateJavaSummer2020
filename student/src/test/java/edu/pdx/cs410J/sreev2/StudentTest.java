package edu.pdx.cs410J.sreev2;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  private Student createPatObject(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }

  @Test
  public void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = createPatObject(name);
    assertThat(pat.getName(), equalTo(name));
  }


  /* when i call toString method check if that contains student name "pat" */
  @Test
  public void testToString() {
    String name = "Pat";
    Student pat = createPatObject(name);
    assertThat(pat.toString(), containsString(name));
  }

}
