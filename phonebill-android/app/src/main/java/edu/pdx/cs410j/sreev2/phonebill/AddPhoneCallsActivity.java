package edu.pdx.cs410j.sreev2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AddPhoneCallsActivity extends AppCompatActivity {
    private static final Pattern PHONE_NUMBER_PATTERN =
            Pattern.compile("^\\d{3}[\\s-]\\d{3}[\\s-]\\d{4}$");
    private static final Pattern DATE_TIME_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$");

    EditText customerName_et;
    EditText callerNumber_et;
    EditText calleeNumber_et;
    EditText start_et;
    EditText end_et;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_calls);

        customerName_et =findViewById(R.id.customerNameEdit);
        callerNumber_et =findViewById(R.id.callerNumberEditText);
        calleeNumber_et =findViewById(R.id.calleeNumberEditText);
        start_et =findViewById(R.id.startEditText);
        end_et =findViewById(R.id.endEditText);

        button = (Button) findViewById(R.id.addOrCreateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName() & validateCallerPhoneNumber() & validateCalleePhoneNumber() & validateStart() & validateEnd()){
                    if(startTimeBeforeEndTime()){
                       // openDialog("good");
                        String customerNameInput = customerName_et.getText().toString().trim();
                        String callerNumberInput = callerNumber_et.getText().toString().trim();
                        String calleeNumberInput = calleeNumber_et.getText().toString().trim();
                        String startInput = start_et.getText().toString().trim();
                        String endInput = end_et.getText().toString().trim();

                        Date startTime = getDateAndTimeInDate(startInput);
                        Date endTime = getDateAndTimeInDate(endInput);
                        String prettyStartTime = getPrettyDateTime(startInput);
                        String prettyEndTime = getPrettyDateTime(endInput);

                        PhoneCall call = new PhoneCall(callerNumberInput, calleeNumberInput, startInput, endInput, startTime, endTime);
                        PhoneBill bill = new PhoneBill(customerNameInput, call);

                       // openDialog(bill.getPhoneCalls().toString());

                        String fileName =customerNameInput + ".txt";
                        String separator = ", ";
//                        String phoneCallDataHeading = customerNameInput+"\n"
//                                + "Caller Number"+ separator + "Callee Number"
//                                + separator + "Start Date and Time"
//                                + separator + "End Date and Time";
                        String phoneCallData = call.getCaller()+ separator + call.getCallee()
                                + separator + prettyStartTime
                                + separator + prettyEndTime
                                + separator + call.callDuration() +"\n";
                        //openDialog(prettyStartTime+" "+prettyEndTime+" "+fileName);
                        FileOutputStream fileOutputStream = null;
                        //FileInputStream fileInputStream = null;
                        try {
                        fileOutputStream = openFileOutput(fileName, MODE_APPEND);
                        fileOutputStream.write(phoneCallData.getBytes());
                        clearAllFields();
                        //fileOutputStream.close();

//                            fileInputStream = openFileInput(fileName);
//                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                            StringBuilder stringBuilder = new StringBuilder();
//                            String lines;
//                            while((lines = bufferedReader.readLine()) != null){
//                                stringBuilder.append(lines).append("\n");
//                            }
//                            openDialog("Information", stringBuilder.toString());

                        } catch(FileNotFoundException ex){
                            openDialog("Error","File does not exists");
                        }catch (IOException e) {
                            openDialog("Error", "error occurred when trying to write to file");
                        }finally {
                            if(fileOutputStream!= null){
                                try {
                                    fileOutputStream.close();
                                   // fileInputStream.close();
                                } catch (IOException e) {
                                    openDialog("Error", "Something went wrong, please try again after some time");
                                }
                            }
                        }
                        openDialog("Information","Phone Call added successfully");
                    }
                }
            }
        });

    }

    private boolean validateName(){
        String customerNameInput = customerName_et.getText().toString().trim();
        if(customerNameInput.isEmpty()){
            customerName_et.setError("Field can't be Empty");
            return false;
        }else{
            customerName_et.setError(null);
            return true;
        }
    }

    private boolean validateCallerPhoneNumber(){
        String callerNumberInput = callerNumber_et.getText().toString().trim();
        if(callerNumberInput.isEmpty()){
            callerNumber_et.setError("Field can't be Empty");
            return false;
        }else if(!PHONE_NUMBER_PATTERN.matcher(callerNumberInput).matches()){
            openDialog("Error", "Given Caller Number is not in correct format\nPlease follow the format\nnnn-nnn-nnnn\nwhere n= number [0-9]\nyou entered:\n" + callerNumberInput);
            return false;
        }else{
            callerNumber_et.setError(null);
            return true;
        }
    }

    private boolean validateCalleePhoneNumber(){
        String calleeNumberInput = calleeNumber_et.getText().toString().trim();
        if(calleeNumberInput.isEmpty()){
            calleeNumber_et.setError("Field can't be Empty");
            return false;
        }else if(!PHONE_NUMBER_PATTERN.matcher(calleeNumberInput).matches()){
            openDialog("Error","Given Callee Number is in wrong format\nPlease follow the format\nnnn-nnn-nnnn where n= number [0-9]\nyou entered:\n" + calleeNumberInput);
            return false;
        }else{
            calleeNumber_et.setError(null);
            return true;
        }
    }

    private boolean validateStart(){
        String startInput = start_et.getText().toString().trim();
        if(startInput.isEmpty()){
            start_et.setError("Field can't be Empty");
            return false;
        }else if(!DATE_TIME_PATTERN.matcher(startInput).matches()){
            openDialog("Error", "Given start date and time are in wrong format\nPlease follow the format\nmm/dd/yyyy hh:mm am/pm\nyou entered:\n" + startInput);
            return false;
        }else{
            start_et.setError(null);
            return true;
        }
    }

    private boolean validateEnd(){
        String endInput = end_et.getText().toString().trim();
        if(endInput.isEmpty()){
            end_et.setError("Field can't be Empty");
            return false;
        }else if(!DATE_TIME_PATTERN.matcher(endInput).matches()){
            openDialog("Error","Given end date and time are in wrong format\nPlease follow the format\nmm/dd/yyyy hh:mm am/pm\nyou entered:\n" + endInput);
            return false;
        }else{
            end_et.setError(null);
            return true;
        }
    }

    private boolean startTimeBeforeEndTime(){
        String startInput = start_et.getText().toString().trim();
        String endInput = end_et.getText().toString().trim();
        Date startTime = getDateAndTimeInDate(startInput);
        Date endTime = getDateAndTimeInDate(endInput);
        if(!startTime.before(endTime)){
            openDialog("Error", "The end date and time should not be equal or before the start date.");
            return false;
        }
        return true;
    }

    public Date getDateAndTimeInDate(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            return date1;
        } catch (ParseException e){
            openDialog("Error","Something went wrong, please try after some time");
            return null;
        }
    }

    public String getPrettyDateTime(String dateTime){
        String pattern = "MM/dd/yy HH:mm a";
        String sdate= dateTime;
        DateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(sdate);
        } catch (ParseException e) {
            openDialog("Error","Something went wrong, please try after some time");
        }
        String dateTimePrettyFormat = df.format(date);
        return dateTimePrettyFormat;
    }

    public void clearAllFields(){
       customerName_et.getText().clear();
       callerNumber_et.getText().clear();
       calleeNumber_et.getText().clear();
       start_et.getText().clear();
       end_et.getText().clear();
    }

    public void openDialog(String t, String s){
        DialogsInPhoneBillApp dialog = new DialogsInPhoneBillApp(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
}