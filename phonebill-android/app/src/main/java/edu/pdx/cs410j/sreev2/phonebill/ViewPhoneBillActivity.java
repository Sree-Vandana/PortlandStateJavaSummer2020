package edu.pdx.cs410j.sreev2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ViewPhoneBillActivity extends AppCompatActivity  {

    EditText customerName_et;
    Button button;
    String customerNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phone_bill);

        customerName_et =findViewById(R.id.viewNameEditText);
        button = (Button) findViewById(R.id.prettyPrintButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName()){
                    customerNameInput = customerName_et.getText().toString().trim();
                    String fileName =customerNameInput + ".txt";

                    try {
                        FileInputStream fileInputStream = null;
                        fileInputStream = openFileInput(fileName);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String callData;

                        while((callData = bufferedReader.readLine()) != null){
                            stringBuilder.append(callData).append("\n");
                        }
                        if(stringBuilder.toString().length() == 0){
                            openDialog("Information", "No phone bill exist for "+customerNameInput);
                        }
                        else{
                            openViewResultActivity();
                        }
                    } catch (FileNotFoundException e) {
                        openDialog("Information", "No phone bill exist for "+customerNameInput);
                        //e.printStackTrace();
                    } catch (IOException e) {
                        openDialog("Information", "Something went wrong, Please try after some time");
                        //e.printStackTrace();
                    }
                    customerName_et.getText().clear();
                }
            }
        });
    }

    public void openViewResultActivity(){
        Intent intent = new Intent(this, ViewResults.class);
        intent.putExtra("customer_name", customerNameInput);
        startActivity(intent);
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

    public void openDialog(String t, String s){
        DialogsInPhoneBillApp dialog = new DialogsInPhoneBillApp(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
}