package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
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
public class StudentTest<name>
{
  public String name = "Pat";

  private Student createPatObject(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }

  @Test
  public void studentNamedPatIsNamedPat() {
    var pat = createPatObject(name);
    assertThat(pat.getName(), equalTo(name));
  }
 /* when i call toString method check if that contains student name "pat"; and gpa */

  @Test
  public void toStringContainsNameGpa(){
    Student pat = new Student("Pat", new ArrayList<>(), 4.0, "Doesn't matter");
    assertThat(pat.toString(), containsString(name +" has a GPA of 4.0"));
  }

  @Ignore
  @Test
  public void wholeStatement(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("Operating Systems");
    classes.add("Java");
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 3 classes: Algorithms, Operating Systems, Java."));
    //   He says \"This class is too much work\"."
  }

  @Test
  public void nameGpaOneClassSize(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 1 class:"));
  }

  @Test
  public void nameGpaTwoClassSize(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("OS");
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 2 classes:"));
  }

  @Test
  public void nameGpaZeroClassSize(){
    ArrayList<String> classes = new ArrayList<>();
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 0 classes.  "));
  }

  @Test
  public void zeroClassesNames(){
    ArrayList<String> classes = new ArrayList<>();
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 0 classes.  "));
  }

  @Test
  public void OneClassesNames(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 1 class: Algorithms.  "));
  }

  @Test
  public void TwoClassesNames(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("OS");
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 2 classes: Algorithms and OS.  "));
  }


  @Test
  public void threeClassesNames(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("OS");
    classes.add("Java");
    Student s = new Student("Sree", classes, 4.0, "Female");
    assertThat(s.toString(), containsString("Sree has a GPA of 4.0 and is taking 3 classes: Algorithms, OS, and Java.  "));
  }

}
