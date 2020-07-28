package edu.pdx.cs410J.sreev2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";
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
     * Returns the phone bill for the given customer
     * @return
     */
   /* public PhoneBill getPhoneBill(String customer) throws IOException, ParserException {
        Response response = get(this.url, Map.of(CUSTOMER_PARAMETER, customer));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        PhoneBillTextParser parser = new PhoneBillTextParser(new StringReader(content));
        return parser.parse();
    }
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

    public String searchForPhoneCalls(final String[] phoneCallValues) throws IOException {
        Response response = get(this.url, Map.of(
                CUSTOMER_PARAMETER, phoneCallValues[0],
                START_TIME_PARAMETER, phoneCallValues[1],
                END_TIME_PARAMETER, phoneCallValues[2]));

        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    public String printEntirePhoneBill(final String customer) throws IOException{
        Response response = get(this.url,Map.of(CUSTOMER_PARAMETER,customer));

        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
        return post(this.url, dictionaryEntries);
    }

    public void removeAllPhoneBills() throws IOException {
        Response response = delete(this.url, Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new PhoneBillRestException(code, response.getContent());
        }
        return response;
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