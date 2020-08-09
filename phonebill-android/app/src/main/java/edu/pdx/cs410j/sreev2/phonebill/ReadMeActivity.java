package edu.pdx.cs410j.sreev2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReadMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);

        TextView readMeContent = (TextView) findViewById(R.id.readMeContent);
        readMeContent.setText("This is README!\nHello World.");
    }
}