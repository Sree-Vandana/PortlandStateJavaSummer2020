package edu.pdx.cs410J.sreev2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillTest {

    @Test
    public void checkIfGetCustomerReturnsName(){
        PhoneBill pb = new PhoneBill("pat", new PhoneCall());
        assertThat(pb.getCustomer(), equalTo("pat"));
    }
}
