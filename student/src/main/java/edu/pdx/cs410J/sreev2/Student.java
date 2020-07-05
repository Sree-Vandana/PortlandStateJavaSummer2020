package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  private final double gpa;
  private final   ArrayList<String> classes;
  private final String gender;

  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.gender = gender;
    this.gpa = gpa;
    this.classes = classes;
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {
    return "He says \"This class is too much work\".";
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    int classesSize = this.classes.size();
    return this.getName() + " has a GPA of " + this.gpa
            + " and is taking "
            + classesSize + " class" + (classesSize != 1 ? "es" : "")
            + (classesSize == 0 ? "." : ":")
            + (classesSize != 0 ? " "+this.classNames()+"." : "")
            + "  "
            + this.says();
  }

  public String classNames(){
    StringBuilder sb = new StringBuilder();
    int classesSize = this.classes.size();
    sb.append(String.join(", ", this.classes.subList(0, classesSize - 1)));
    if(classesSize > 1) {
      if(classesSize == 2) {
        sb.append(" and ");
      }
      else{
        sb.append(", and ");
      }
    }
    sb.append(this.classes.get(classesSize - 1));
    return sb.toString();
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    System.err.println("Missing command line arguments");
    System.err.println("Hello");
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithms");
    classes.add("OS");
    classes.add("Java");
    String name = "sree";
    Double gpa = 4.0;
    String gender = "female";
    Student sreev2 = new Student(name, classes, gpa, gender);
    System.out.println("student obj = "+ sreev2);
    String output = sreev2.toString();
    System.out.println("output = "+output);

    /*for (String arg : args) {
      System.out.println(arg);
    }*/
    System.exit(1);
  }
}