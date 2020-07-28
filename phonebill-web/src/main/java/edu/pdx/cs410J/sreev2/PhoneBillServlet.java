package edu.pdx.cs410J.sreev2;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{

    public final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customerName = getParameter(CUSTOMER_PARAMETER, request);
        String startTime = getParameter(START_TIME_PARAMETER, request);
        String endTime = getParameter(END_TIME_PARAMETER, request);

        if(customerName == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
        }
        else if(startTime == null & endTime == null) {
            printEntirePhoneBill(request, response);
        }
        else if(startTime == null){
            missingRequiredParameter(response, START_TIME_PARAMETER);
        }
        else if(endTime == null){
            missingRequiredParameter(response, END_TIME_PARAMETER);
        }
        else{
            if(validateStartAndEndTime(request, response))
                searchForPhoneCalls(request, response);
        }
    }

    private boolean validateStartAndEndTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String start = getParameter(START_TIME_PARAMETER, request);
        String end = getParameter(END_TIME_PARAMETER, request);

        Date startTime = getDateAndTimeInDate(start);
        Date endTime = getDateAndTimeInDate(end);
        String regexDate = "^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$";
        if(!Pattern.matches(regexDate, start)){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.malformattedDateOrTime(START_TIME_PARAMETER));
            return false;
        }
        if(!Pattern.matches(regexDate, end)){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.malformattedDateOrTime(END_TIME_PARAMETER));
            return false;
        }
        if (!startTime.before(endTime)){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.startTimeBeforEndTime());
            return false;
        }
        return true;
    }
    /**
     * this method formats the date and time in SimpleDateTime format
     * parses the given date and time into a <type>Date</type>
     * @param dateTime
     *        both start and end date in <type>String</type>
     * @return date
     *        of <type>Date</type>
     * */
    public Date getDateAndTimeInDate(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            return date1;
        } catch (ParseException e){
            System.err.println("Date parsing error");
        }

        return null;
    }

    /**
     * Handles an HTTP POST request by storing the phonecalls in a PhoneBill
     * It writes the calls in phonebills <class>HashMap</class>
     * which is of <type><String, PhoneBill></type>
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        ArrayList<String> list = new ArrayList<>();

        list.add(getParameter(CUSTOMER_PARAMETER, request));
        list.add(getParameter(CALLER_NUMBER_PARAMETER, request));
        list.add(getParameter(CALLEE_NUMBER_PARAMETER, request));
        list.add(getParameter(START_TIME_PARAMETER, request));
        list.add(getParameter(END_TIME_PARAMETER, request));

        String parameter= "";
        for(int i=0; i<list.size(); i++) {
            if(list.get(i) == null) {
                parameter= (i==0)? CUSTOMER_PARAMETER
                        : (i==1)? CALLER_NUMBER_PARAMETER
                        : (i==2)? CALLEE_NUMBER_PARAMETER
                        : (i==3)? START_TIME_PARAMETER
                        : END_TIME_PARAMETER;
                missingRequiredParameter(response, parameter);
                return;
            }
        }

        if(validateStartAndEndTime(request, response) & validatePhoneCalls(request, response)){

        PhoneCall call = new PhoneCall(list.toArray(new String[0]));

        if(this.phoneBills.get(list.get(0)) == null)
            this.phoneBills.put(list.get(0), new PhoneBill(list.get(0),call));
        else {
            var customer_phoneBill = this.phoneBills.get(list.get(0));
            customer_phoneBill.addPhoneCall(call);
            this.phoneBills.put(list.get(0), customer_phoneBill);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        String message = "HTTP Status: "+response.getStatus()+"\n"+Messages.addedPhoneCallMessage(call);
        PrintWriter pw = response.getWriter();
        pw.println(message);

        pw.flush();
        }
    }

    private boolean validatePhoneCalls(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String caller = getParameter(CALLER_NUMBER_PARAMETER, request);
        String callee = getParameter(CALLEE_NUMBER_PARAMETER, request);

        String regexDate = "^\\d{3}[\\s-]\\d{3}[\\s-]\\d{4}$";
        if(!Pattern.matches(regexDate, caller)){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.malformattedPhoneCall(CALLER_NUMBER_PARAMETER));
            return false;
        }
        if(!Pattern.matches(regexDate, callee)){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.malformattedPhoneCall(CALLEE_NUMBER_PARAMETER));
            return false;
        }
        return true;
    }

    private void printEntirePhoneBill(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String customerName = getParameter(CUSTOMER_PARAMETER, request);

        PhoneBill bill = phoneBills.get(customerName);

        if(bill == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.customerDoesNotHaveAPhoneBill(customerName));
            return;
        }

        PhoneBillPrettyPrinter prettyPrinter = new PhoneBillPrettyPrinter();
        String prettyPhoneBill = prettyPrinter.getPrettyPhoneCalls(bill);
        PrintWriter pw = response.getWriter();
        pw.println(prettyPhoneBill);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    private final void searchForPhoneCalls(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String customerName = getParameter(CUSTOMER_PARAMETER, request);

        PhoneBill bill = phoneBills.get(customerName);
        if(bill == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.customerDoesNotHaveAPhoneBill(customerName));
        }

        //String format of date and time
        String prettyPhoneCalls = "";
        var startDateTime = new Date(getParameter(START_TIME_PARAMETER, request));
        var endDateTime = new Date(getParameter(END_TIME_PARAMETER, request));

        try{
            prettyPhoneCalls = bill.searchPhoneCallsBetween(startDateTime, endDateTime);
        }catch (InvalidParameterException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }

        PrintWriter pw = response.getWriter();
        pw.println(prettyPhoneCalls);
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**---> change the dict term in here...
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.phoneBills.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }

    @VisibleForTesting
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }
}