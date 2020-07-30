package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
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
    private String customerName = "vandana";
    private String callerNumber = "123-456-7890";
    private String calleeNumber = "203-123-1234";
    private String startDateTime = "01/15/2020 1:10 am";
    private String endDateTime = "01/15/2020 1:30 pm";

    private String customerName0 = "vandana";
    private String callerNumber0 = "123-456-7890";
    private String calleeNumber0 = "203-123-1234";
    private String startDateTime0 = "01/16/2020 3:30 am";
    private String endDateTime0 = "01/16/2020 4:30 am";

    PhoneCall call = new PhoneCall(List.of(customerName, callerNumber,calleeNumber,startDateTime,endDateTime).toArray(new String[0]));
    PhoneCall call0 = new PhoneCall(List.of(customerName0, callerNumber0,calleeNumber0,startDateTime0,endDateTime0).toArray(new String[0]));

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
    public void addPhoneCallAndGetAPhoneCall() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        client.removeAllPhoneBills();
        String[] args ={customerName, callerNumber,calleeNumber,startDateTime,endDateTime};
        String status = client.addPhoneCall(args);

        assertThat(status, containsString("Phone call added successfully"));

        String phoneCalls = client.printEntirePhoneBill(customerName);

        assertThat(phoneCalls, containsString(customerName));
        assertThat(phoneCalls, containsString(callerNumber));
        assertThat(phoneCalls, containsString(calleeNumber));
    }


    @Test
    public void testingSearchBetweenMethod() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        client.removeAllPhoneBills();
        String[] args ={customerName, callerNumber,calleeNumber,startDateTime,endDateTime};
        String[] args0 = {customerName0, callerNumber0,calleeNumber0,startDateTime0,endDateTime0};
        String status = client.addPhoneCall(args);
        status = client.addPhoneCall(args0);
        assertThat(status, containsString("Phone call added successfully"));

        String phoneCalls = client.searchForPhoneCalls(new String[]{customerName, "01/15/2020 1:10 am", "01/15/2020 11:59 pm"});

        assertThat(phoneCalls, containsString("Phone Calls between dates"));
    }

    @Test
    public void testingSearchBetweenMethodempty() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        client.removeAllPhoneBills();
        String[] args ={customerName, callerNumber,calleeNumber,startDateTime,endDateTime};
        String[] args0 = {customerName0, callerNumber0,calleeNumber0,startDateTime0,endDateTime0};
        String status = client.addPhoneCall(args);
        status = client.addPhoneCall(args0);
        assertThat(status, containsString("Phone call added successfully"));

        String phoneCalls = client.searchForPhoneCalls(new String[]{customerName, "09/15/2020 1:10 am", "09/15/2020 11:59 pm"});

        assertThat(phoneCalls, containsString("No Phone calls found between"));
    }

}