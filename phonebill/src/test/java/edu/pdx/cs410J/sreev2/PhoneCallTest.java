package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  @Ignore
  @Test(expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall();
    call.getStartTimeString();
  }

  @Ignore
  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getCallee(), containsString("not implemented"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test
  public void getCallerMethodReturnsPhoneNumber(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumber(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCallee(), containsString("123-123-1234"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDate(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getStartTimeString(), containsString("19:39"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDate(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:40"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getEndTimeString(), containsString("19:40"));
  }

  @Ignore
  @Test
  public void argsShouldNotBeEmpty(){
    String args = "";
    PhoneCall pc = new PhoneCall();
  }


  
}
