package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillTest {

    @Test
    public void checkIfGetCustomerReturnsName(){
        PhoneBill pb = new PhoneBill("pat", new PhoneCall());
        assertThat(pb.getCustomer(), equalTo("pat"));
    }

    @Test
    public void addPhoneCallAcceptsOnlyPhoneCallObjects(){
        PhoneBill pb = new PhoneBill();
        pb.addPhoneCall(new PhoneCall());
    }

    @Test
    public void phoneBillHasAdefaultConstructor(){
        PhoneBill pb = new PhoneBill();
    }

    @Ignore
    @Test
    public void testingsearchBetweenDatesMethod(){
        String args[] ={"sree", "111-222-3333", "000-999-8888", "01/15/2020", "10:30", "am" , "01/15/2020", "11:40", "am"};
        String args0[] ={"sree", "111-222-3333", "000-999-8888", "01/16/2020", "10:30", "am" , "01/16/2020", "11:40", "am"};
        String args1[] ={"sree", "111-222-3333", "000-999-8888", "01/17/2020", "10:30", "am" , "01/17/2020", "11:40", "am"};
        String args2[] ={"sree", "111-222-3333", "000-999-8888", "01/18/2020", "10:30", "am" , "01/18/2020", "11:40", "am"};

        PhoneCall call = new PhoneCall(args);
        PhoneCall call0 = new PhoneCall(args0);
        PhoneCall call1 = new PhoneCall(args1);
        PhoneCall call2 = new PhoneCall(args2);

        PhoneBill bill = new PhoneBill("sree");
        bill.addPhoneCall(call);
        bill.addPhoneCall(call0);
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);

        Date d1 = new Date("01/15/2020 10:30 am");
        Date d2 = new Date("01/16/2020 11:40 am");

        String ans = bill.searchPhoneCallsBetween(d1, d2);
        assertThat(ans, containsString("Phone Calls between dates"));

    }
}
