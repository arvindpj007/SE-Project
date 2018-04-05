package info.androidhive.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.collections4.map.MultiValueMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

public class Functionalities extends AppCompatActivity {
    private Button addTran,showTrans;
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat, RefTran,RefCatSum;
    private Button addCat,catTrans,SetBudget,Analysis, Profile, Set,home;

    MultiValueMap<String, String> catgTrans = MultiValueMap.multiValueMap(new LinkedHashMap<String,Collection<String>>(),(Class<LinkedHashSet<String>>) (Class<?>)LinkedHashSet.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionalities);
        Analysis =(Button) findViewById(R.id.button6);
        addTran = (Button) findViewById(R.id.addTran);
        showTrans=(Button) findViewById(R.id.showTrans);
        Profile = (Button)findViewById(R.id.profileTrial);
        Set = (Button)findViewById(R.id.settingsTrial);
        addCat= findViewById(R.id.addCat);
        catTrans=findViewById(R.id.CatTrans);
        SetBudget=findViewById(R.id.setBudget);

        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");
        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefTran = RefUid.child("Transactions");

        RefCatSum=RefUid.child("CatSum");

        home=findViewById(R.id.Home);

        RefTran.addChildEventListener(new com.firebase.client.ChildEventListener() {
            String amount,cat,shname,shDay,shMonth,shYear;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int i=0;

                for (DataSnapshot S:dataSnapshot.getChildren()) {

                    switch(i)
                    {
                        case 0:
                            amount=S.getValue().toString().trim();
                            break;
                        case 1:
                            cat=S.getValue().toString().trim();
                            break;

                    }

                    i++;
                }
                catgTrans.put(cat,amount);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        showTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this, ShowTransActivity.class));
            }
        });
        addTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this, Transac.class));
            }
        });
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this, AddCat.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this, HomeActivity.class));
            }
        });
        catTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this,CategTrans.class));
            }
        });
        SetBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this,SetBud.class));
            }
        });
        Analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Iterator<String> mapIter = catgTrans.keySet().iterator();

                while(mapIter.hasNext()) {
                    String key = mapIter.next();
//                    Toast.makeText(getApplicationContext(), "Value: " + key + ":" + catgTrans.get(key), Toast.LENGTH_SHORT).show();

                    Collection<String> val = catgTrans.getCollection(key);
                    SumTrans obj = new SumTrans();
                    RefCatSum.child(key).setValue(obj.computeSum(val).toString());



                    Intent i = new Intent(Functionalities.this,AnalysisActivity.class);
                    startActivity(i);


                }

            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(Functionalities.this,ProfileActivity.class));
            }
        });

        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Functionalities.this,SettingsActivity.class));
            }
        });

    }


}
