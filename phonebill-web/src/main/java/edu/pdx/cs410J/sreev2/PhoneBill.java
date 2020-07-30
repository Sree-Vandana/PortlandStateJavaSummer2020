package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.util.*;

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
    private ArrayList<PhoneCall> phoneCalls= new ArrayList<>();

    /**
     * Constructor for PhoneBill class, with no parameters.
     * */
    public PhoneBill(){
        super();
    }

    /**
     * Constructor of PhoneBill class, with one parameter
     * @param name
     *          Customer Name
     * */
    public PhoneBill(String name){
        super();
        customerName = name;
    }

    /**
     * Constructor of PhoneBill class, with parameters
     * @param name
     *          Customer Name
     * @param newPhoneCallRecord
     *          This is Customers new/first phone call record.
     * */
    public PhoneBill(String name, PhoneCall newPhoneCallRecord){
        super();
        customerName = name;
        addPhoneCall(newPhoneCallRecord);
    }

    /**
     * This constructor copies all the phone calls into phonecalls collection
     * @param name of type <code>String</code>
     *             Coustomer Name
     * @param bills of type <class>PhoneBill</class>
     * */
    public PhoneBill(final String name, PhoneBill... bills){
        super();
        customerName = name;
        copyPhoneBills(bills);
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
    public void addPhoneCall(final PhoneCall phoneCall) {
        phoneCalls.add(phoneCall);
    }

    /**
     * <method>getPhoneCalls</method> returns <class>Collection</class> of phoneCall records.
     * */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        Collections.sort(phoneCalls, PhoneBill.StartTimeComparator);
        return this.phoneCalls;
    }

    /**
     * This method is invoked by the constructor and
     * copies all the given phone calls into <Collection>phoneCalls</Collection>
     * @throws InvalidParameterException
     *         when the phone bills contains different customer name
     * */
    private void copyPhoneBills(PhoneBill... bills){
        for(var bill : bills){
            if(bill != null && customerName.equals(bill.customerName)) {
                phoneCalls.addAll(bill.getPhoneCalls());
                //Collections.sort(phoneCalls);
            } else{
                throw new InvalidParameterException("PhoneBills does not contain the same customer");
            }
        }
    }

    /**
     * The <Collections>ArrayList</Collections> are sorted in this method
     * and store in <Collections>Phonecalls</Collections>
     * */
    public static Comparator<PhoneCall> StartTimeComparator = new Comparator<PhoneCall>() {
        public int compare(PhoneCall p1, PhoneCall p2) {
            Date StartTime1 = p1.getStartTime();
            Date StartTime2 = p2.getStartTime();
            String CallerNum1 = p1.getCaller();
            String CallerNum2 = p2.getCaller();

            //ascending order
            if(StartTime1.compareTo(StartTime2) == 0){
                return CallerNum1.compareTo(CallerNum2);
            }
            return StartTime1.compareTo(StartTime2);
        }};


    public final PhoneBill searchPhoneCallsBetween(Date startDateTime, Date endDateTime)throws InvalidParameterException {
        String prettyPhoneCalls = null;
        PhoneBill bill = new PhoneBill(this.customerName);
        PhoneBillPrettyPrinter prettyPrinter = new PhoneBillPrettyPrinter();

        var start = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(startDateTime);
        var end = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(endDateTime);

        if(!startDateTime.before(endDateTime))
            throw new InvalidParameterException("Start date and time can not be equals or after the end date and time of the phone call");

        var pc = getPhoneCalls();
        for(PhoneCall c: pc){
            var date = c.getStartTime();
            if((date.after(startDateTime) || startDateTime.equals(date)) && (date.before(endDateTime) || endDateTime.equals(date))){
                bill.addPhoneCall(c);
            }
        }

        /*if (bill.getPhoneCalls().isEmpty())
            prettyPhoneCalls = "No Phone calls found between " + start  +" and " + end;
        else
            prettyPhoneCalls = "Phone Calls between dates "+ start + " and " + end +":\n" + prettyPrinter.getPrettyPhoneCalls(bill);
*/
        return bill;
    }
}
