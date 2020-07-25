package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

public class PhoneBillPrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

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
}
