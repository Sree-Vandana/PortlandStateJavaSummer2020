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

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test
  public void getCallerMethodReturnsPhoneNumber(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "p");

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumber(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "p");

    assertThat(pc.getCallee(), containsString("123-123-1234"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDate(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "p");

    assertThat(pc.getStartTimeString(), containsString("1/15/2020 19:39"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDate(){
    String[] strArray = new String[]{"-print", "sree", "123-123-1234", "123-123-1234", "1/15/2020", "19:39", "1/15/2020", "19:40"};
    PhoneCall pc = new PhoneCall(strArray, "p");

    assertThat(pc.getEndTimeString(), containsString("1/15/2020 19:40"));
  }

  @Test
  public void getCallerMethodReturnsPhoneNumberWhenGiventStatus(){
    String[] strArray = new String[]{"-textFile", "sreefile", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "t");

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumberWhenGiventStatus(){
    String[] strArray = new String[]{"-textFile", "sreefile", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "t");

    assertThat(pc.getCallee(), containsString("321-123-4567"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDateWhenGiventStatus(){
    String[] strArray = new String[]{"-textFile","sreefile", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/16/2020", "20:39"};
    PhoneCall pc = new PhoneCall(strArray, "t");

    assertThat(pc.getStartTimeString(), containsString("1/15/2020 19:39"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDateWhenGiventStatus(){
    String[] strArray = new String[]{"-textFile", "sreefile", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/16/2020", "20:40"};
    PhoneCall pc = new PhoneCall(strArray, "t");

    assertThat(pc.getEndTimeString(), containsString("1/16/2020 20:40"));
  }

  @Test
  public void getCallerMethodReturnsPhoneNumberWhenGiventpStatus(){
    String[] strArray = new String[]{"-textFile", "sreefile", "-print", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "tp");

    assertThat(pc.getCaller(), containsString("123-123-1234"));
  }

  @Test
  public void getCalleeMethodReturnsPhoneNumberWhenGiventpStatus(){
    String[] strArray = new String[]{"-print", "-textFile", "sreefile", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/15/2020", "19:39"};
    PhoneCall pc = new PhoneCall(strArray, "tp");

    assertThat(pc.getCallee(), containsString("321-123-4567"));
  }

  @Test
  public void getStartTimeStringReturnsAStartTimeAndDateWhenGiventpStatus(){
    String[] strArray = new String[]{"-textFile","sreefile", "-print", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/16/2020", "20:39"};
    PhoneCall pc = new PhoneCall(strArray, "tp");

    assertThat(pc.getStartTimeString(), containsString("1/15/2020 19:39"));
  }

  @Test
  public void getEndtTimeStringReturnsEndTimeAndDateWhenGiventpStatus(){
    String[] strArray = new String[]{"-print", "-textFile", "sreefile", "sree", "123-123-1234", "321-123-4567", "1/15/2020", "19:39", "1/16/2020", "20:40"};
    PhoneCall pc = new PhoneCall(strArray, "tp");

    assertThat(pc.getEndTimeString(), containsString("1/16/2020 20:40"));
  }
  
}
