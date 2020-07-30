package edu.pdx.cs410J.sreev2;

import org.junit.Test;

import java.io.IOException;

public class PhoneBillPrettyPrinterTest {

    @Test
    public void giveingCallToPrettyPrintAddsThemToPrettyFile() throws IOException {
        PhoneBill bill = new PhoneBill("sree");
        PhoneCall call1 = new PhoneCall(new String[]{"sree", "123-123-4567","123-123-1233", "11/20/2018 03:21 PM",
                "11/22/2018 2:22 AM"});
        PhoneCall call2 = new PhoneCall(new String[]{"sree","123-123-7655","678-677-2345", "03/22/2018 9:19 PM",
                "03/21/2019 2:22 AM"});
        PhoneCall call3 = new PhoneCall(new String[]{"sree","075-445-123","689-667-7886", "03/12/2018 12:30 PM",
                "03/21/2020 2:22 PM"});
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);
        PhoneBillPrettyPrinter prettyPrinter = new PhoneBillPrettyPrinter("03/12/2018 12:30 PM","03/21/2019 2:22 AM" );
        prettyPrinter.getPrettyPhoneCalls(bill);
    }
}
