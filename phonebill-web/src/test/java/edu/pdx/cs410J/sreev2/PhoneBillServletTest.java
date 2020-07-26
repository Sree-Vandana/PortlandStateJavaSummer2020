package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  private String customerName = "sree";
  private String callerNumber = "123-456-7890";
  private String calleeNumber = "203-123-1234";
  private String startDateTime = "01/15/2020 1:10 am";
  private String endDateTime = "01/15/2020 1:30 pm";

  private String customerName0 = "sree";
  private String callerNumber0 = "123-456-7890";
  private String calleeNumber0 = "203-123-1234";
  private String startDateTime0 = "01/15/2020 3:30 am";
  private String endDateTime0 = "01/15/2020 4:30 am";

  private String customerName1 = "vandana";
  private String callerNumber1 = "012-123-7890";
  private String calleeNumber1 = "203-456-1234";
  private String startDateTime1 = "02/16/2020 11:10 am";
  private String endDateTime1 = "02/16/2020 12:30 pm";

  private String customerName2 = "vandana";
  private String callerNumber2 = "456-267-4789";
  private String calleeNumber2 = "123-123-1234";
  private String startDateTime2 = "01/15/2020 3:30 am";
  private String endDateTime2 = "01/15/2020 4:30 am";

  private String print = "print";
  private String nullprint = "null";
  private String emptyprint = "";



  @Test
  public void requestWithNoCustomerReturnMissingParameter() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(CUSTOMER_PARAMETER));
  }

  @Test
  public void requestCustomerWithNoPhoneBillReturnsNotFound() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    String customerName = "Dave";
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customerName);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customerName));

  }

  @Ignore //delete it later
  @Test
  public void addPhoneCallToPhoneBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Customer";
    String callerPhoneNumber = "503-123-4567";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(callerPhoneNumber);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    verify(pw, times(0)).println(Mockito.any(String.class));
    verify(response).setStatus(HttpServletResponse.SC_OK);

    PhoneBill phoneBill = servlet.getPhoneBill(customer);
    assertThat(phoneBill, notNullValue());
    assertThat(phoneBill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(callerPhoneNumber));
  }

  @Test
  public void requestingExistingPhoneBillDumpsItToPrintWriter() throws IOException, ServletException {
    String customer = "Customer";
    String callerPhoneNumber = "503-123-4567";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(callerPhoneNumber));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String textPhoneBill = sw.toString();
    assertThat(textPhoneBill, containsString(customer));
    assertThat(textPhoneBill, containsString(callerPhoneNumber));

  }

  @Test
  public void testingdoPostMethodWithPrintOption() throws IOException, ServletException {
    PhoneBillServlet servlet = new PhoneBillServlet();
    PhoneCall call1 = new PhoneCall(List.of(customerName, callerNumber,calleeNumber,startDateTime,endDateTime).toArray(new String[0]));
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customerName);
    when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(callerNumber);
    when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(calleeNumber);
    when(request.getParameter(START_TIME_PARAMETER)).thenReturn(startDateTime);
    when(request.getParameter(END_TIME_PARAMETER)).thenReturn(endDateTime);
    when(request.getParameter(PRINT_PARAMETER)).thenReturn(print);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    verify(pw).println(Messages.addedPhoneCallMessage(call1, print));
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

}