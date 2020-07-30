package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * this class is used to pretty print customers phonebill.
 * */
public class PhoneBillPrettyPrinter{
    /**
     * start of <type>String</type>
     * */
    private String start;

    /**
     * end of <type>String</type>
     * */
    private String end;

    /**
     * writer of <class>PrintWriter</class>
     * */
    private PrintWriter writer;

    /**
     * num of <type>int</type>
     * */
    private int num;

    /**
     * this is the default prettyprinter constructor
     * */
    public PhoneBillPrettyPrinter(){
        super();
        this.num = 0;
    }

    /**
     * PhoneBillPrettyPrinter Constructor which take 2 parameter
     * @param start <type>String</type>
     *              start time
     * @param end <type>String</type>
     *            end Time
     * */
    public PhoneBillPrettyPrinter(String start, String end){
        this.start = start;
        this.end = end;
        this.num = 1;
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
        String prettyPhoneCalls = "";

        if(num == 1){
            if (bill.getPhoneCalls().isEmpty()) {
                prettyPhoneCalls = "For "+bill.getCustomer()+" No Phone calls found between " + this.start + " and " + this.end;
                return prettyPhoneCalls;
            }
            else{
                prettyPhoneCalls = "Phone Calls between dates "+ start + " and " + end +":\n";
            }
        }

        prettyPhoneCalls += "Customer: "+ bill.getCustomer() + "\n";
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
