package com.example.android.xpenseauditor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddCategoryActivity extends AppCompatActivity {

    EditText catName,budget;
    AppCompatButton submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        catName= (EditText) findViewById(R.id.newCategoryName);
        budget=(EditText) findViewById(R.id.newBudget);

        submit=(AppCompatButton) findViewById(R.id.submit_addCat);

    }

}
