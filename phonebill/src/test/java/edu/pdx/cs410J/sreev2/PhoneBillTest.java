package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.sandwich.util.Assert.assertEquals;

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
    public void getPhoneCallMethodReturnsCollectionOfPhoneCallsInArrayList(){
        PhoneBill pb = new PhoneBill();
        assertEquals(pb.getPhoneCalls(), new ArrayList<>());
    }

    @Ignore
    @Test
    public void whatPhoneCallContains(){
        String[] strArray = new String[]{"sree", "123-123-1234", "123-123-1234", "1/15/2020 19:39", "1/15/2020 19:39"};
        PhoneCall pc = new PhoneCall(strArray);
        PhoneBill pb = new PhoneBill("sree", pc);
        //assertThat(pb.getPhoneCalls(), equalTo(pc));
    }

}
