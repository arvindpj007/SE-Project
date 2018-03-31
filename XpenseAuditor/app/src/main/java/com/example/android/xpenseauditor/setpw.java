package com.example.android.xpenseauditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class setpw extends AppCompatActivity {
    Button l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpw);


        l= (Button) findViewById(R.id.btn_log);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(setpw.this, LoginActivity.class));
                finish();
            }
        });


    }
}
