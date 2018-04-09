package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class CatExpPer extends AppCompatActivity {
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat;
    String catSum="",budget="";
    TextView catn,catgbudg,catsum,catper,exp;
    int sum, budg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_exp_per);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);

        catn=findViewById(R.id.catn);
        catgbudg=findViewById(R.id.catgbudg);
        catsum=findViewById(R.id.catsum);
        catper=findViewById(R.id.catper);
        exp=findViewById(R.id.exp);
        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("DateRange").child(month+"-"+year).child("CatTran");
        Intent intent = getIntent();
        String SelCat = intent.getStringExtra("name");
        catn.setText(SelCat);

        RefUid.child("DateRange").child(month+"-"+year).child("CatSum").child(SelCat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catSum = dataSnapshot.getValue().toString().trim();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefUid.child("Categories").child(SelCat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                budget = dataSnapshot.getValue().toString().trim();
                sum = Integer.parseInt(catSum);
                budg = Integer.parseInt(budget);
                catgbudg.setText("Rs."+budget);
                catsum.setText("Rs."+catSum);
                if(sum>budg){
                    exp.setText("Budget Exceeded by");
                    catper.setText("Rs."+String.valueOf(sum-budg));
                }
                else{
                    exp.setText("Per Budget Remaining");
                    catper.setText(String.valueOf((int)((((float)(budg-sum)/budg))*100))+"%");
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });





    }
}
