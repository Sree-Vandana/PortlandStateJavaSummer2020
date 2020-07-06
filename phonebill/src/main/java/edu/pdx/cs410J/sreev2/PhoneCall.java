package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

public class PhoneCall extends AbstractPhoneCall{

  private String callerNum;
  private String calleeNum;
  private String startTimeString;
  private String endTimeString;


  public PhoneCall() {
    super();
  }

  public PhoneCall(final String[] args){
    this.callerNum = args[2];
    this.calleeNum = args[3];
    this.startTimeString = args[5];
    this.endTimeString = args[7];

  }

  @Override
  public String getCaller() {
   // throw new UnsupportedOperationException("This method is not implemented yet");
    return callerNum;
  }

  @Override
  public String getCallee() {
    return calleeNum;
  }


  @Override
  public Date getStartTime() {
    return super.getStartTime();
  }

  @Override
  public Date getEndTime() {
    return super.getEndTime();
  }

  @Override
  public String getStartTimeString() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return startTimeString;
  }

  @Override
  public String getEndTimeString() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return endTimeString;
  }


}
