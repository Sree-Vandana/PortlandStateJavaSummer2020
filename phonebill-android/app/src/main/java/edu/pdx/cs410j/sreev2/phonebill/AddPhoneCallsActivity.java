package edu.pdx.cs410j.sreev2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AddPhoneCallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_calls);

        EditText customerNameTextView =findViewById(R.id.customerNameEdit);
    }
}