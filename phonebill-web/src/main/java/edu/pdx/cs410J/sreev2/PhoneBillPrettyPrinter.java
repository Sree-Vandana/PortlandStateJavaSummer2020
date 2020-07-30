package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * this class is used to pretty print customers phonebill.
 * */
public class PhoneBillPrettyPrinter{
    private PrintWriter writer;

    /**
     * this is the default prettyprinter constructor
     * */
    public PhoneBillPrettyPrinter(){
        super();
    }

    /**
     * This method pretty projnts the phonecalls.
     * @param bill <class>PhoneBill</class>
     *             phonebill of a customer
     * @return prettybill of type <type>String</type>
     * */
    public String getPrettyPhoneCalls(PhoneBill bill) {
        var num_of_phoneCalls = bill.getPhoneCalls().size();
        int count = num_of_phoneCalls;
        String prettyPhoneCalls = "Customer: "+ bill.getCustomer() + "\n";
        prettyPhoneCalls += "Number of phone calls: " + num_of_phoneCalls + "\n";
        prettyPhoneCalls += "#     Caller Phone Number     Callee Phone Number     Call Started At      Call Ended At     Call Duration\n";
        prettyPhoneCalls += "------------------------------------------------------------------------------------------------------------\n";
        for (PhoneCall c : bill.getPhoneCalls()) {
            prettyPhoneCalls += String.format("%-8d %-23s %-19s %-19s %-18s %8d Minutes\n",num_of_phoneCalls - --count, c.getCaller(),c.getCallee(),
                    c.getPrettyDateTime(1), c.getPrettyDateTime(2), c.callDuration());
        }
        return prettyPhoneCalls;
    }
}
