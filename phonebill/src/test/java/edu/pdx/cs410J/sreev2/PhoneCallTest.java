package edu.pdx.cs410J.sreev2;

import org.hamcrest.core.StringContains;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
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

  @Test
  public void getCallerMethodReturnsPhoneNumber(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumber(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCallee(), containsString("123-123-1234"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDate(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getStartTimeString(), containsString("01/15/2020 1:39 AM"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDate(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getEndTimeString(), containsString("01/15/2020 1:39 PM"));
  }

  @Test
  public void getCallerMethodReturnsPhoneNumberWhenGiventStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumberWhenGiventStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCallee(), containsString("123-123-1234"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDateWhenGiventStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getStartTimeString(), containsString("01/15/2020 1:39 AM"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDateWhenGiventStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getEndTimeString(), containsString("01/15/2020 1:39 PM"));
  }

  @Test
  public void getCallerMethodReturnsPhoneNumberWhenGiventpStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumberWhenGiventpStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getCallee(), containsString("123-123-1234"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDateWhenGiventpStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getStartTimeString(), containsString("01/15/2020 1:39 AM"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDateWhenGiventpStatus(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getEndTimeString(), containsString("01/15/2020 1:39 PM"));
  }

  @Test
  public void getStartTimeStringReturnsStartTimeAndDateWhenGivenStatust(){
    String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "01/15/2020 1:39 AM", "01/15/2020 1:39 PM"};
    PhoneCall pc = new PhoneCall(strArray);

    assertThat(pc.getStartTimeString(), containsString("01/15/2020 1:39 AM"));
  }

  @Test
  public void getStartTimeStringReturnsStartTimeAndDateWhenGivenStatus1(){
    String[] strArray = new String[]{"123-123-1234", "123-123-1234", "01/15/2020", "1:39", "AM", "01/15/2020", "1:39", "PM"};
    PhoneCall pc = new PhoneCall(strArray, 1);

    assertThat(pc.getStartTimeString(), containsString("01/15/2020 1:39 AM"));
  }

  @Test
  public void checkingDuration(){
    String[] strArray = new String[]{"sree","123-123-1234", "321-123-4567", "01/15/2020 1:40 AM", "01/15/2020 1:42 AM"};
    PhoneCall pc = new PhoneCall(strArray);
    assertThat(String.valueOf(pc.callDuration()), containsString("2"));
  }
  
}
