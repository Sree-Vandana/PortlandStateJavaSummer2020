package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

public class PhoneCall extends AbstractPhoneCall{

  public PhoneCall() {
    super();
  }

  @Override
  public String getCaller() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getCallee() {
    return "This method is not implemented yet";
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
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

}
