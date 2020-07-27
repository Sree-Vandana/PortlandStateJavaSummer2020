package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.*;
import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.END_TIME_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;


/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllPhoneBills() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllPhoneBills();
  }

  @Test
  public void test1NonexistentPhoneBillThrowsException() throws IOException, ParserException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    try {
      client.printEntirePhoneBill("Dave");
      fail("Should have thrown a PhoneBillRestException");

    } catch (PhoneBillRestClient.PhoneBillRestException ex) {
      assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
      //assertThat(ex.getMessage(), equalTo("abc"));

    }
  }

  @Test
  public void test2AddingPhoneCall() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
      String args[] = {"sree", "123-456-7890", "503-256-7890", "01/15/2020 10:30 am", "01/15/2020 10:40 am"};
      //client.addPhoneCall(true, args);
      //fail("Should have thrown a PhoneBillRestException");
      HttpRequestHelper.Response response = client.postToMyURL(Map.of(
              PRINT_PARAMETER, "print",
              CUSTOMER_PARAMETER, args[0],
              CALLER_NUMBER_PARAMETER, args[1],
              CALLEE_NUMBER_PARAMETER, args[2],
              START_TIME_PARAMETER, args[3],
              END_TIME_PARAMETER, args[4]));
      assertThat(response.getContent(), containsString("Phone call added successfully"));
  }

  @Test
  public void test3SearchForAPhoneCall() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String args0[] = {"sree", "123-456-7890", "503-256-7890", "01/15/2020 10:30 am", "01/15/2020 11:30 am"};
    String args1[] = {"sree", "123-456-7890", "493-256-7890", "01/16/2020 10:40 am", "01/16/2020 11:20 am"};
    String args2[] = {"sree", "123-456-7890", "468-256-7890", "01/17/2020 10:50 am", "01/17/2020 11:10 am"};
    String args3[] = {"sree", "123-456-7890", "503-256-7890", "01/18/2020 10:10 am", "01/18/2020 11:00 am"};

    addPhoneCallsInTest(client, args0);
    addPhoneCallsInTest(client, args1);
    addPhoneCallsInTest(client, args2);
    addPhoneCallsInTest(client, args3);

    String search[] = {"-search", "sree", "01/15/2020 10:30 am", "01/16/2020 10:40 am"};
    String ans = client.searchForPhoneCalls(search);
    assertThat(ans, containsString("Phone Calls between dates"));
  }

  @Test
  public void test4PrintEntirePhoneBillWhenGivenOnlyCustomerName() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();

    String args0[] = {"vandana", "123-456-7890", "503-256-7890", "02/15/2020 10:30 am", "02/15/2020 11:30 am"};
    String args1[] = {"vandana", "123-456-7890", "493-256-7890", "02/16/2020 10:40 am", "02/16/2020 11:20 am"};
    String args2[] = {"vandana", "123-456-7890", "468-256-7890", "02/17/2020 10:50 am", "02/17/2020 11:10 am"};
    String args3[] = {"vandana", "123-456-7890", "503-256-7890", "02/18/2020 10:10 am", "02/18/2020 11:00 am"};

    addPhoneCallsInTest(client, args0);
    addPhoneCallsInTest(client, args1);
    addPhoneCallsInTest(client, args2);
    addPhoneCallsInTest(client, args3);

    String ans = client.printEntirePhoneBill("vandana");
    assertThat(ans, containsString("Customer: vandana"));

  }

  private void addPhoneCallsInTest(PhoneBillRestClient client, String[] args) throws IOException {
    HttpRequestHelper.Response response = client.postToMyURL(Map.of(
            PRINT_PARAMETER, "",
            CUSTOMER_PARAMETER, args[0],
            CALLER_NUMBER_PARAMETER, args[1],
            CALLEE_NUMBER_PARAMETER, args[2],
            START_TIME_PARAMETER, args[3],
            END_TIME_PARAMETER, args[4]));
  }

  @Ignore
  @Test
  public void test2AddPhoneCall() throws IOException, ParserException {
    /*PhoneBillRestClient client = newPhoneBillRestClient();
    String customer = "Customer";
    String caller = "123-456-7890";
    client.addPhoneCall(customer, caller);

    PhoneBill phoneBill = client.printEntirePhoneBill(customer);
    assertThat(phoneBill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(caller));*/
  }

}