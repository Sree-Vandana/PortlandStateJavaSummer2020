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

import static edu.pdx.cs410J.sreev2.PhoneBillServlet.CALLER_PARAMETER;
import static edu.pdx.cs410J.sreev2.PhoneBillServlet.CUSTOMER_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  @Test
  public void requestWithNoCustomerReturnsMissingParameter() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
//    PrintWriter pw = mock(PrintWriter.class);
//
//    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

   // verify(pw).println(Messages.missingRequiredParameter("customer"));
    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(CUSTOMER_PARAMETER));
  }

  @Test
  public void requestCustomerWithNoPhoneBillReturnsNotFound() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    String customerName = "sree";
    when(request.getParameter("customer")).thenReturn(customerName);
    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customerName));

  }

  /*@Test
  public void addPhoneCallToPhoneBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "sree";
    String callerPhoneNumber = "503-123-1234";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customer);
    when(request.getParameter("callerNumber")).thenReturn(callerPhoneNumber);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    // Use a StringWriter to gather the text from multiple calls to println()
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

//    assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));
//
//    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
//    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(pw, times(0)).println(any(String.class));
    verify(response).setStatus(HttpServletResponse.SC_OK);

    PhoneBill phonebill = servlet.getPhoneBill(customer);
    assertThat(phonebill, notNullValue());
    assertThat(phonebill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phonebill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(callerPhoneNumber));
  }*/

  @Test
  public void addPhoneCallToPhoneBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "sree";
    String callerPhoneNumber = "503-123-4567";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(CALLER_PARAMETER)).thenReturn(callerPhoneNumber);

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

}
