package com.example.android.xpenseauditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {
    private DatabaseReference mRootRef;
    private DatabaseReference RefUid;
    private DatabaseReference RefName;
    private FirebaseAuth auth;

    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        auth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        RefUid= mRootRef.child(auth.getUid());
        RefName = RefUid.child("Name");

        userName=(TextView) findViewById(R.id.testview);

        RefUid.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot DS) {
                String n=DS.getValue(String.class);
                Toast.makeText(getApplicationContext(),n,Toast.LENGTH_LONG).show();
                userName.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
