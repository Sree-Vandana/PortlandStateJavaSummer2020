package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

public class PhoneCall extends AbstractPhoneCall{

  /**
   * callerNum, calleeNum, startTimeString, endTimeString of type <code>String</code>
   * */
  private String callerNum;
  private String calleeNum;
  private String startTimeString;
  private String endTimeString;

  /**
   * Default constructor
   * */
  public PhoneCall() {
    super();
  }

  /**
   * Constructor PhoneCall assigns value to global values
   * @param args args <code>String</code>
   * */
  public PhoneCall(final String[] args, String status){
    if(status.equals("p")) {
      this.callerNum = args[2];
      this.calleeNum = args[3];
      this.startTimeString = args[4] + " " + args[5];
      this.endTimeString = args[6] + " " + args[7];
    }
    else if(status.equals("t")){
      this.callerNum = args[3];
      this.calleeNum = args[4];
      this.startTimeString = args[5] + " " + args[6];
      this.endTimeString = args[7] + " " + args[8];
    }
    //if(status.equals("tp"))
    else{
      this.callerNum = args[4];
      this.calleeNum = args[5];
      this.startTimeString = args[6] + " " + args[7];
      this.endTimeString = args[8] + " " + args[9];
    }

  }

  /**
   * will be accessed by TextParser file
   * */
  public PhoneCall(final String[] args, int status){
    this.callerNum = args[0];
    this.calleeNum = args[1];
    this.startTimeString = args[2];
    this.endTimeString = args[3];
  }

  /**
   * <method>getCaller</method> has no parameter
   * @return callerNum of type <code>String</code>
   * */
  @Override
  public String getCaller() {
    return callerNum;
  }

  /**
   * <method>getCallee</method> has no parameter
   * @return calleeNum of type <code>String</code>
   * */
  @Override
  public String getCallee() {
    return calleeNum;
  }

  /**
   * <method>getStartTime</method> has no parameter
   * @return satrtTime of type <code>Date</code>
   * */
  @Override
  public Date getStartTime() {
    return super.getStartTime();
  }

  /**
   * <method>getEndTime</method> has no parameter
   * @return endTime of type <code>Date</code>
   * */
  @Override
  public Date getEndTime() {
    return super.getEndTime();
  }

  /**
   * <method>getStartTimeString</method> has no parameter
   * @return satrtTime of type <code>String</code>
   * */
  @Override
  public String getStartTimeString() {
    return startTimeString;
  }

  /**
   * <method>getEndTime</method> has no parameter
   * @return endTime of type <code>String</code>
   * */
  @Override
  public String getEndTimeString() {
    return endTimeString;
  }

}
