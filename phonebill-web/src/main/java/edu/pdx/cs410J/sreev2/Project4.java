package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String customer = null;
        String caller = null;

        for (String arg : args) {
            if (hostName == null) {
                hostName = arg;

            } else if ( portString == null) {
                portString = arg;

            } else if (customer == null) {
                customer = arg;

            } else if (caller == null) {
                caller = arg;

            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        String message;
 /*       try {
            if (customer == null) {
            } else if (caller == null) {
                try {
                    PhoneBill bill = client.printEntirePhoneBill(customer);
                    PrintWriter pw = new PrintWriter(System.out, true);
                    PhoneBillPrettyPrinter pretty = new PhoneBillPrettyPrinter(pw);
                    pretty.dump(bill);
                } catch (ParserException e) {
                    System.err.println("Could not parse response!");
                    System.exit(1);
                } catch (PhoneBillRestClient.PhoneBillRestException ex) {
                    switch (ex.getHttpStatusCode()) {
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            System.err.println("No phone bill for customer " + customer);
                            System.exit(1);
                            return;
                        default:
                    }
                }
            } else {
                // Post the customer/caller pair
                client.addPhoneCall(customer, caller);
            }
        } catch ( IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }*/

        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }
}