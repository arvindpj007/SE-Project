package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class SMSTransShow extends AppCompatActivity {
    TextView smstid,smstamnt,smsshpname,smscat,smsdate,sms;
    private Firebase mRootRef;
    private Firebase RefUid;
    String d,m,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smstrans_show);
        Intent i = getIntent();
        String tid = i.getStringExtra("indexPos");
        Toast.makeText(getApplicationContext()," yo : "+tid,Toast.LENGTH_SHORT).show();
        smstid=findViewById(R.id.smstid);
        smstid.setText(tid);
        smstamnt=findViewById(R.id.smstamnt);
        smsshpname=findViewById(R.id.smsshpname);
        smscat=findViewById(R.id.smscat);
        smsdate=findViewById(R.id.smsdate);
        sms=findViewById(R.id.sms);


        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);

        RefUid.child("UnCatTran").child(tid).child("Amount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    smstamnt.setText(dataSnapshot.getValue().toString().trim());
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefUid.child("UnCatTran").child(tid).child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    smscat.setText(dataSnapshot.getValue().toString().trim());
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefUid.child("UnCatTran").child(tid).child("Shop Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    smsshpname.setText(dataSnapshot.getValue().toString().trim());
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefUid.child("UnCatTran").child(tid).child("ZMessage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    sms.setText(dataSnapshot.getValue().toString().trim());
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefUid.child("UnCatTran").child(tid).child("Day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    d = dataSnapshot.getValue().toString().trim();
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefUid.child("UnCatTran").child(tid).child("Month").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    m = dataSnapshot.getValue().toString().trim();
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RefUid.child("UnCatTran").child(tid).child("Year").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    y = dataSnapshot.getValue().toString().trim();
                    smsdate.setText(d + "/" + m + "/" + y);
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SMSTransShow.this,HomeActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
