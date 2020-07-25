package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PhoneCall extends AbstractPhoneCall {

    /**
     * callerNum, calleeNum, startTimeString, endTimeString of type <code>String</code>
     * */
    private String callerNum;
    private String calleeNum;
    private String startTimeString;
    private String endTimeString;
    private Date startTime;
    private Date endTime;

    /**
     * Default constructor
     * */
    public PhoneCall() {
        super();
    }

    public PhoneCall(String caller)
    {
        callerNum = caller;
    }

    /**
     * @param args <code>String</code>
     *             command line arguments
     * Constructor PhoneCall assigns value to global values
     * */
    public PhoneCall(final String[] args){
        this.callerNum = args[1];
        this.calleeNum = args[2];
        this.startTimeString = args[3];
        this.endTimeString = args[4];
        this.startTime = getDateAndTimeInDate(getStartTimeString());
        this.endTime = getDateAndTimeInDate(getEndTimeString());
        if (!this.startTime.before(this.endTime)){
            System.err.println("Start date and time can not be equals or after the end date and "
                    + "time of the phone call");
            System.exit(1);
        }
    }

    /**
     * @param args <type>String[]</type>
     *             command line arguments
     * @param status <type>int</type>
     *               this is an int type status, this will be accessed by <class>TextParser</class>
     * */
    public PhoneCall(final String[] args, int status){
        this.callerNum = args[0];
        this.calleeNum = args[1];
        this.startTimeString = args[2] + " " +args[3] + " " + args[4];
        this.endTimeString = args[5] + " " +args[6] + " " + args[7];
        this.startTime = getDateAndTimeInDate(getStartTimeString());
        this.endTime = getDateAndTimeInDate(getEndTimeString());
        if (!this.startTime.before(this.endTime)){
            System.err.println("Start date and time can not be equals or after the end date and "
                    + "time of the phone call");
            System.exit(1);
        }
    }


    public long callDuration() {
        long diffInMillies = Math.abs(getEndTime().getTime() - getStartTime().getTime());
        return TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    /**
     * <method>getCaller</method> has no parameter
     * @return callerNum of type <code>String</code>
     * */
    @Override
    public String getCaller() {
        return callerNum;
    }

    /**
     * <method>getCallee</method> has no parameter
     * @return calleeNum of type <code>String</code>
     * */
    @Override
    public String getCallee() {
        return calleeNum;
    }

    /**
     * <method>getStartTime</method> has no parameter
     * @return satrtTime of type <code>Date</code>
     * */
    @Override
    public Date getStartTime() {
        //return super.getStartTime();
        return startTime;
    }

    /**
     * <method>getEndTime</method> has no parameter
     * @return endTime of type <code>Date</code>
     * */
    @Override
    public Date getEndTime() {
        //return super.getEndTime();
        return endTime;
    }

    /**
     * <method>getStartTimeString</method> has no parameter
     * @return satrtTime of type <code>String</code>
     * */
    @Override
    public String getStartTimeString() {
        return startTimeString;
    }

    /**
     * <method>getEndTime</method> has no parameter
     * @return endTime of type <code>String</code>
     * */
    @Override
    public String getEndTimeString() {
        return endTimeString;
    }


    /**
     * The date and time that is entered in the pretty file is formatted by this method to look good
     * @param i <type>Integer</type>
     *          represents start or end time
     * @return dateTimeprettyFormat of <type>String</type>
     * */
    public String getPrettyDateTime(int i){
        String pattern = "MM/dd/yy HH:mm a";
        String sdate="";
        DateFormat df = new SimpleDateFormat(pattern);
        if(i==1){
            sdate = getStartTimeString();
        }
        if(i==2){
            sdate = getEndTimeString();
        }
        Date date = null;
        try {
            date = df.parse(sdate);
        } catch (ParseException e) {
            System.err.println("ErrorOccured while parsing to pretty date-time format");
        }
        String dateTimeprettyFormat = df.format(date);
        return dateTimeprettyFormat;
    }

    /**
     * this method formats the date and time in SimpleDateTime format
     * parses the given date and time into a <type>Date</type>
     * @param dateTime
     *        both start and end date in <type>String</type>
     * @return date
     *        of <type>Date</type>
     * */
    public Date getDateAndTimeInDate(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            return date1;
        } catch (ParseException e){
            System.err.println("Date parsing error");
            System.exit(1);
        }

        return null;
    }

}