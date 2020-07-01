package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill{

    private String customerName;
    private ArrayList<PhoneCall> phoneCalls = new ArrayList<>();

    public PhoneBill() {
        super();
    }

    public PhoneBill(String name, PhoneCall pcalls){
        super();
        customerName = name;
        phoneCalls.add(pcalls);
    }

    @Override
    public String getCustomer() {
        return customerName;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {

    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return phoneCalls;
    }
}
