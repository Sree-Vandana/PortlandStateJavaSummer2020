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
        readMeContent.setText("This is README!\n" +
                "In this Android Application users can perform the following functioanlities" +
                "\n1. Add phone calls to exsisting PhoneBill\n" +
                "2. Create a new phonebill\n" +
                "3. View the entire Phonebill of a customer\n" +
                "4. search for phonecalls between dates\n" +
                "* To Add to or Create a Phonebill, click on Add/Create Button\n" +
                "* To view the entire Phonebill of an exsisting customer click on View Button\n" +
                "* To Search for phonecalls between two dates, click on Search Button\n" +
                "\n" +
                "All the Button will lead you to forms, fill out each from to perform appropriate funtionality.\n" +
                "Each form can have certail fields and certain format to follow\n" +
                "**All fields are required fields**\n" +
                "* Customer Name: name of the customer\n" +
                "* Caller/Callee Number: nnn-nnn-nnnn where n= number [0-9]\n" +
                "* Start/End DateTime: mm/dd/yyyy hh:mm am/pm\n");
    }
}