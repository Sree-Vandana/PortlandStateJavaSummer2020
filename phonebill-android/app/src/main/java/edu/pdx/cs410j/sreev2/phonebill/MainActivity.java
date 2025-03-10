package edu.pdx.cs410j.sreev2.phonebill;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private Button button;
TextView mainAppDiscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainAppDiscription = findViewById(R.id.mainAppDiscription);
        mainAppDiscription.setText("Create, add, view or search phone calls using this application, using the buttons below.\n" +
                "Go to README for additional Help. ");

        button = (Button) findViewById(R.id.readMeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReadMeActivity();
            }
        });

        button = (Button) findViewById(R.id.addOrCreateMainButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddOrCreateActivity();
            }
        });

        button = (Button) findViewById(R.id.viewMainButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewPhoneBillActivity();
            }
        });

        button = (Button) findViewById(R.id.searchMainButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchPhoneCallsActivity();
            }
        });

    }

    public void openReadMeActivity() {
        Intent intent = new Intent(this, ReadMeActivity.class);
        startActivity(intent);
    }

    public void openAddOrCreateActivity() {
        Intent intent = new Intent(this, AddPhoneCallsActivity.class);
        startActivity(intent);
    }

    public void openViewPhoneBillActivity() {
        Intent intent = new Intent(this, ViewPhoneBillActivity.class);
        startActivity(intent);
    }

    public void openSearchPhoneCallsActivity() {
        Intent intent = new Intent(this, SearchForCallsActivity.class);
        startActivity(intent);
    }

}