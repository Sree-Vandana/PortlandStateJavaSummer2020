package edu.pdx.cs410j.sreev2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResult extends AppCompatActivity {

    TextView searchResultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchResultTextView = findViewById(R.id.searchResultTextView);
        Intent intent = getIntent();
        String customerName = intent.getStringExtra("value_key");
        String startInput = intent.getStringExtra("start_key");
        Date startTime = getDateAndTimeInDate(startInput);
        String endInput = intent.getStringExtra("end_key");
        Date endTime = getDateAndTimeInDate(endInput);
        String fileName = customerName+".txt";

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader fileData = new BufferedReader(inputStreamReader);
            final List<String> listOfPhoneCalls = new ArrayList<>();
            String strCurrentLine;
            String contentHead = "The phone calls between "+startInput+" and "+endInput+" for customer "+customerName;
            String content = "";
            while((strCurrentLine = fileData.readLine()) != null){
                listOfPhoneCalls.add(strCurrentLine);
            }
            for(String phoneCall: listOfPhoneCalls) {
                String[] args = phoneCall.split(", ");
                Date dateFromFile = getDateAndTimeInDate(args[2]);
                if((dateFromFile.after(startTime) || startTime.equals(dateFromFile)) && (dateFromFile.before(endTime) || endTime.equals(dateFromFile))) {
                    content += phoneCall + "\n\n";
                }
            }
            if(content.length() == 0){
                content = "\n\nNo phone calls available between the given dates";
            }
            else{
                contentHead += "\n\nCallerNumber, CalleeNumber, StartDate, EndDate, CallDuration\n"
                        + "-----------------------------------------------------------------------------------\n\n";
            }
                searchResultTextView.setText(contentHead + "" +content);
        } catch (FileNotFoundException e) {
            openDialog("Error", "File Does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            openDialog("Error", "IO error occured");
            e.printStackTrace();
        }
        //searchResultTextView.setText("search result "+customerName);
    }

    public Date getDateAndTimeInDate(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            return date1;
        } catch (ParseException e){
            openDialog("Error","date parsing error");
            return null;
        }
    }

    public void openDialog(String t, String s){
        DialogsInPhoneBillApp dialog = new DialogsInPhoneBillApp(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
}