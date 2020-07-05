package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{

    /**
     * customer Name of type <code>String</code>
     * */
    private String customerName;

    /**
     * <code>ArrayList</code> of type <code>PhoneCall</code>
     * Holds all phone calls records.
     * For project 1, it holds one <code>PhoneCall</code> record.
     * */
    final private ArrayList<PhoneCall> phoneCalls= new ArrayList<>();

    /**
     * Constructor for PhoneBill class, with no parameters.
     * */
    public PhoneBill(){
        super();
    }

    /**
     * Constructor of PhoneBill class, with parameters
     * @param name
     *          Customer Name
     * @param newPhoneCallRecord
     *          This is Customers new/first phone call record.
     *
     * */
    public PhoneBill(String name, PhoneCall newPhoneCallRecord){
        super();
        customerName = name;
        addPhoneCall(newPhoneCallRecord);
    }

    /**
     * <method>getCustomer</method> has no parameter
     * @return customerName
     * */
    @Override
    public String getCustomer() {
        return customerName;
    }

    /***
     * <method>addPhoneCall</method> adds a new record to the <type>ArrayList</type>
     * @param phoneCall
     *          phoneCall is of type <class>PhoneCall</class>
     */
    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
            phoneCalls.add(phoneCall);
    }

    /**
     * <method>getPhoneCalls</method> returns <class>Collection</class> of phoneCall records.
     * */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return phoneCalls;
    }
}
