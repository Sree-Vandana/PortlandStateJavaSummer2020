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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ViewResults extends AppCompatActivity {

    TextView viewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        viewResult = (TextView) findViewById(R.id.viewResultTextView);

        Intent intent = getIntent();
        String customer_name = intent.getStringExtra("customer_name");

            String fileName =customer_name + ".txt";

            try {
                FileInputStream fileInputStream = null;
                fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String callData;

                while((callData = bufferedReader.readLine()) != null){
                    stringBuilder.append(callData).append("\n\n");
                }

                if(stringBuilder.toString().length() == 0){
                    openDialog("Information", "No phone calls exist for "+customer_name);
                }
                else{
                    viewResult.setText("Customer Name: "+customer_name + "\n\n"
                            + "CallerNumber, CalleeNumber, StartDate, EndDate, CallDuration\n"
                            + "-----------------------------------------------------------------------------------\n\n"
                            + stringBuilder.toString());
                }
            } catch (FileNotFoundException e) {
                openDialog("in catch block", "No phone calls exist for "+customer_name);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void openDialog(String t, String s){
        DialogsInPhoneBillApp dialog = new DialogsInPhoneBillApp(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
}