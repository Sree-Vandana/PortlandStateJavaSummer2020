package edu.pdx.cs410j.sreev2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SearchForCallsActivity extends AppCompatActivity {

    private Button button;
    EditText customerName_et;
    EditText start_et;
    EditText end_et;

    String splitString;

    private static final Pattern DATE_TIME_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_calls);

        customerName_et =findViewById(R.id.searchCustomerNameEditText);
        start_et = findViewById(R.id.searchStartEditText);
        end_et = findViewById(R.id.searchEndEditText);

        button = (Button) findViewById(R.id.searchPhoneCallButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName() & validateStart() & validateEnd()){
                    if(startTimeBeforeEndTime()){

                        String customerNameInput = customerName_et.getText().toString().trim();
                        String fileName =customerNameInput + ".txt";

                        try {
                            FileInputStream fileInputStream = openFileInput(fileName);
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            StringBuilder stringBuilder = new StringBuilder();
                            String callData;

                            while((callData = bufferedReader.readLine()) != null){
                                stringBuilder.append(callData).append("\n");
                            }
                            if(stringBuilder.toString().length() == 0){
                                openDialog("Information", "No phone calls exist for "+customerNameInput);
                            }
                            else{
                                openSearchResultActivity();
                            }
                            clearAllFields();
                        } catch (FileNotFoundException e) {
                            openDialog("Error", "No PhoneBill found for customer "+customerNameInput);
                            //e.printStackTrace();
                        } catch (IOException e) {
                            openDialog("Error", "Something went wrong, please try again after sometime");
                            //e.printStackTrace();
                        }
//                        finally {
//                            clearAllFields();
//                        }
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
            openDialog("Error","date parsing error");
            return null;
        }
    }

    public void clearAllFields(){
        customerName_et.getText().clear();
        start_et.getText().clear();
        end_et.getText().clear();
    }

    private void openSearchResultActivity() {
        Intent intent = new Intent(this, SearchResult.class);
        String customerNameInput = customerName_et.getText().toString().trim();
        String startInput = start_et.getText().toString().trim();
        String endInput = end_et.getText().toString().trim();
        intent.putExtra("value_key", customerNameInput);
        intent.putExtra("start_key", startInput);
        intent.putExtra("end_key", endInput);
        startActivity(intent);
    }

    public void openDialog(String t, String s){
        DialogsInPhoneBillApp dialog = new DialogsInPhoneBillApp(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
}