package edu.pdx.cs410J.sreev2;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String formatWordCount(int count )
    {
        return String.format( "Dictionary on server contains %d words", count );
    }

    public static String formatDictionaryEntry(String word, String definition )
    {
        return String.format("  %s : %s", word, definition);
    }


    /**
     * notifies the user that the required parameter id missing
     * @param parameterName <type>String</type>
     *                      name of the parametr that is missing
     * @return information
     * */

    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * notifies the user that the given
     * customer name does not have an exsisting phonebill
     * @param customerName <type>String</type>
     *                      name of the customer
     * @return information
     * */

    public static String customerDoesNotHaveAPhoneBill( String customerName )
    {
        return String.format("'%s' does not have a exsisting PhoneBill", customerName);
    }

    public static String definedWordAs(String word, String definition )
    {
        return String.format( "Defined %s as %s", word, definition );
    }

    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

    public static Map.Entry<String, String> parseDictionaryEntry(String content) {
        Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find()) {
            return null;
        }

        return new Map.Entry<>() {
            @Override
            public String getKey() {
                return matcher.group(1);
            }

            @Override
            public String getValue() {
                String value = matcher.group(2);
                if ("null".equals(value)) {
                    value = null;
                }
                return value;
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException("This method is not implemented yet");
            }
        };
    }

    public static void formatDictionaryEntries(PrintWriter pw, Map<String, String> dictionary) {
        pw.println(Messages.formatWordCount(dictionary.size()));

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            pw.println(Messages.formatDictionaryEntry(entry.getKey(), entry.getValue()));
        }
    }

    public static Map<String, String> parseDictionary(String content) {
        Map<String, String> map = new HashMap<>();

        String[] lines = content.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            Map.Entry<String, String> entry = parseDictionaryEntry(line);
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    /**
     * notifies the user that the given
     * customer name does not have an exsisting phonebill
     * @param customerName <type>String</type>
     *                      name of the customer
     * @return information
     * */

    static String noPhoneBillForCustomer(String customerName) {
        return String.format("No phone bill for customer %s", customerName);
    }

    /**
     * notifies the user that the given
     * phonecall is succesfully added
     * @param call <class>PhoneCall</class>
     *                      phonecall of a customer
     * @return information
     * */
    public static String addedPhoneCallMessage(PhoneCall call) {
        return ("Phone call added successfully.");
    }

    /**
     * notifies the user that the given
     * start time id before the end time
     * @return information
     * */
    public static String startTimeBeforEndTime() {
        return ("Start date and time can not be equals or after the end date and "
                + "time of the phone call");
    }

    /**
     * notifies the user that the given
     * start time or end time are malformatted
     * @return information
     * */
    public static String malformattedDateOrTime(String TimeParameter, String date) {
        final String format = "DateTime: MM/dd/yyy hh:mm am/pm";
        return String.format("Given %s is not in correct format. please follow the format %s (Given: %s)", TimeParameter, format, date);
    }

    /**
     * notifies the user that the given
     * phonecall is  malformatted
     * @param callerNumberParameter <type>String</type>
     * @return information
     * */
    public static String malformattedPhoneCall(String callerNumberParameter) {
        final String format = "nnn-nnn-nnnn where n = number [0-9]";
        return String.format("Given %s is not in correct format. please follow the format %s", callerNumberParameter, format);

    }

  /*  public static String noWordGiven(String word) {
        return ("no word : "+word);
    }*/
}