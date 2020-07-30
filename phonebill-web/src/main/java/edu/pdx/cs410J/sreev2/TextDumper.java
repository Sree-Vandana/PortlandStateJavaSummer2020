package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    /**
     * argument path of <type>String</type>
     * hold sthe path or file name which need to be written
     */
    private static String path;

    /**
     * @param writer
     * @throws IOException By calling this constructor, the file will be created with the given fileName.
     * @p the name or path of the file in which data is written
     */
    public TextDumper(PrintWriter writer) throws IOException {
        this.writer = writer;
    }

    /**
     * @param phoneBill <class>PhoneBill</class>
     *                  The PhoneBill object which holds the information of phonecall that need to be written in a file
     * @throws IOException This method, enters the phone call information in my defined format into a file.
     */
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {

        String separator = ",";

        this.writer.println(phoneBill.getCustomer() + "\n"
                + "Caller Number" + separator + "Callee Number"
                + separator + "Start Date and Time"
                + separator + "End Date and Time");
        for(PhoneCall phoneCall : phoneBill.getPhoneCalls()) {
            this.writer.println(phoneCall.getCaller() + separator + phoneCall.getCallee()
                    + separator + phoneCall.getStartTimeString()
                    + separator + phoneCall.getEndTimeString());
        }


        /*fileHasSameCustomerName(phoneBill.getCustomer());
        String separator = ",";
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file, true);
        var phoneCalls = phoneBill.getPhoneCalls();
        if (file.length() == 0) {
            fileWriter.append(phoneBill.getCustomer() + "\n"
                    + "Caller Number" + separator + "Callee Number"
                    + separator + "Start Date and Time"
                    + separator + "End Date and Time");
        } else {
            fileWriter.append("");
        }
        for (PhoneCall phoneCall : phoneCalls) {
            fileWriter.append("\n" + phoneCall.getCaller() + separator + phoneCall.getCallee()
                    + separator + phoneCall.getStartTimeString()
                    + separator + phoneCall.getEndTimeString());
        }
        fileWriter.flush();
        fileWriter.close();*/


    }
}