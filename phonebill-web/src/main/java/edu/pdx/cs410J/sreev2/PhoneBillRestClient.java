package edu.pdx.cs410J.sreev2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    /**
     * WEB_APP of <type>String</type>
     * */
    private static final String WEB_APP = "phonebill";

    /**
     * SERVLET of <type>String</type>
     * */
    private static final String SERVLET = "calls";

    /**
     * url of <type>String</type>
     * */
    private final String url;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Adds the phone call for the given customer to exsisting bill or creates new.
     * @param phoneCallValues <type>String[]</type>
     *                        phonecall arguments
     * @return responce content sent by the server
     */
  public String addPhoneCall(final String[] phoneCallValues) throws IOException {
        Response response = postToMyURL(Map.of(
                CUSTOMER_PARAMETER, phoneCallValues[0],
                CALLER_NUMBER_PARAMETER, phoneCallValues[1],
                CALLEE_NUMBER_PARAMETER, phoneCallValues[2],
                START_TIME_PARAMETER, phoneCallValues[3],
                END_TIME_PARAMETER, phoneCallValues[4]));

        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    /**
     * searches the phone calls between two dates of a given customer.
     * @param phoneCallValues <type>String[]</type>
     *                        phonecall arguments
     * @return responce content sent by the server
     */
    public String searchForPhoneCalls(final String[] phoneCallValues) throws IOException, ParserException {
        Response response = get(this.url, Map.of(
                CUSTOMER_PARAMETER, phoneCallValues[0],
                START_TIME_PARAMETER, phoneCallValues[1],
                END_TIME_PARAMETER, phoneCallValues[2]));

        throwExceptionIfNotOkayHttpStatus(response);

        String searchedCalls = response.getContent();
        TextParser parser = new TextParser(new StringReader(searchedCalls));
        //return parser.parse();

        PhoneBillPrettyPrinter prettyPrinter = new PhoneBillPrettyPrinter(phoneCallValues[1], phoneCallValues[2]);
        return prettyPrinter.getPrettyPhoneCalls(parser.parse());
    }

    /**
     * pretty prints the entire phonebill of a customer
     * @param customer <type>String</type>
     *                        phonecall arguments customer String
     * @return responce content sent by the server
     */
    public String printEntirePhoneBill(final String customer) throws IOException, ParserException {
        Response response = get(this.url,Map.of(CUSTOMER_PARAMETER,customer));

        throwExceptionIfNotOkayHttpStatus(response);
        //return response.getContent();
        String entirePhoneBill = response.getContent();
        TextParser parser = new TextParser(new StringReader(entirePhoneBill));

        PhoneBillPrettyPrinter prettyPrinter = new PhoneBillPrettyPrinter();
        return prettyPrinter.getPrettyPhoneCalls(parser.parse());
    }

    /**
     * removes all phonecalls of a phonebill
     */
    public void removeAllPhoneBills() throws IOException {
        Response response = delete(this.url, Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * this method throws exception if the responce is not SK_OK
     * @param response <type>Responce</type>
     *                        phonecall arguments customer String
     * @return responce content sent by the server
     * @throws PhoneBillRestException
     */
    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new PhoneBillRestException(code, response.getContent());
        }
        return response;
    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
        return post(this.url, dictionaryEntries);
    }

    @VisibleForTesting
    class PhoneBillRestException extends RuntimeException {
        private final int httpStatusCode;

        PhoneBillRestException(int httpStatusCode, String message) {
            super("Got an HTTP Status Code of " + httpStatusCode+"\nMessage: "+message);
            this.httpStatusCode = httpStatusCode;
        }

        public int getHttpStatusCode() {
            return this.httpStatusCode;
        }
    }

}