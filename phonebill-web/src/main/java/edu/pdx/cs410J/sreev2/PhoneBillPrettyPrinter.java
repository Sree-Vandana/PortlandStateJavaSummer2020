package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

public class PhoneBillPrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private PrintWriter writer;

    public PhoneBillPrettyPrinter(){
        super();
    }

    public PhoneBillPrettyPrinter(PrintWriter pw) {
        this.writer = pw;
    }

    @Override
    public void dump(PhoneBill bill) throws IOException {
        this.writer.println(bill.getCustomer());

        for(PhoneCall call : bill.getPhoneCalls()) {
            this.writer.println("  " + call.getCaller());
        }

        this.writer.flush();
    }

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
