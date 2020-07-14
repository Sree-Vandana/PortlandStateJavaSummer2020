package edu.pdx.cs410J.sreev2;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

}
