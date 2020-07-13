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
  public PhoneCall(final String[] args){
    this.callerNum = args[2];
    this.calleeNum = args[3];
    this.startTimeString = args[4] + " " + args[5];//args[4] +" "+ args[5]
    this.endTimeString = args[6] + " " + args[7];//args[6] +" "+ args[7]
  }

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
