package edu.pdx.cs410J.sreev2;

import org.junit.Test;

import java.io.IOException;

public class PrettyPrintTest {
    @Test
    public void testPrintAndWriteToFileAtSameTime() throws IOException {
        PhoneBill bill = new PhoneBill("sree");
        PhoneCall Phone1 ,Phone2, Phone3, Phone4, Phone5;
        bill.addPhoneCall(Phone1 = new PhoneCall(new String[]{"sree", "123-123-4567","123-123-1233", "11/20/2018 03:21 PM",
                "11/22/2018 2:22 AM"}));
        bill.addPhoneCall(Phone2 = new PhoneCall(new String[]{"sree","123-123-7655","678-677-2345", "03/22/2018 9:19 PM",
                "03/21/2019 2:22 AM"}));
        bill.addPhoneCall(Phone3 = new PhoneCall(new String[]{"sree","075-445-123","689-667-7886", "03/12/2018 12:30 PM",
                "03/21/2020 2:22 PM"}));
        bill.addPhoneCall(Phone4 = new PhoneCall(new String[]{"sree","234-567-2938","123-543-5677", "02/16/2018 11:50 PM",
                "03/21/2020 2:22 AM"}));
        bill.addPhoneCall(Phone5 = new PhoneCall(new String[]{"sree","654-455-245","969-059-6905", "09/27/2018 3:22 PM",
                "03/21/2020 2:22 AM"}));
        PrettyPrinter prettyPrinter = new PrettyPrinter( "prettyFileTest");
        prettyPrinter.printOnStandardIO(bill);
        prettyPrinter.dump(bill);
    }
}
