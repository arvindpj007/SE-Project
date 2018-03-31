package com.example.android.xpenseauditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    DatePicker dob;
    Button submit;
    EditText userName, userEmail, userPhone, userAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dob = (DatePicker)findViewById(R.id.userDOB);
        submit = (Button)findViewById(R.id.submitButton);
        userAddress = (EditText)findViewById(R.id.userAddress);
        userEmail = (EditText)findViewById(R.id.userEmail);
        userName = (EditText)findViewById(R.id.userName);
        userPhone = (EditText)findViewById(R.id.userPhone);

        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String day = String.valueOf(dob.getDayOfMonth());
        String month = String.valueOf(dob.getMonth())+1;
        String year = String.valueOf(dob.getYear());
        String dateOfBirth = day+"/"+month+"/"+year;
        Toast.makeText(getApplicationContext(),dateOfBirth,Toast.LENGTH_LONG).show();

        String name = userName.getText().toString();
        String phone = userPhone.getText().toString();
        String addr = userAddress.getText().toString();
        String email = userEmail.getText().toString();
        Toast.makeText(getApplicationContext(),name+","+addr+","+phone+","+email,Toast.LENGTH_LONG).show();
    }
}
