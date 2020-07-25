package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

public class PhoneBillTextDumper implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    PhoneBillTextDumper(PrintWriter writer) {
        this.writer = writer;

    }

    @Override
    public void dump(PhoneBill bill) throws IOException {
        this.writer.println(bill.getCustomer());

        for(PhoneCall call : bill.getPhoneCalls()) {
            this.writer.println(call.getCaller());
        }
    }
}
