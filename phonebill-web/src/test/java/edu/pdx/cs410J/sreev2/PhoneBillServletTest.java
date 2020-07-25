package edu.pdx.cs410J.sreev2;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.CALLER_NUMBER_PARAMETER;
import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.CUSTOMER_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

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

}