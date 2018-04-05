package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetBud extends AppCompatActivity {
    private ListView catBudView;
    private ArrayList<String> CatgBudg=new ArrayList<>();
    private ArrayList<String> Catg=new ArrayList<>();
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bud);
        catBudView = (ListView) findViewById((R.id.CatBud));


        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        final SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.setbudlist, new String[] {"Cat", "Bud"}, new int[] {R.id.text1, R.id.text2});



        catBudView.setAdapter(adapter);


        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("Categories");

        RefCat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value1= dataSnapshot.getKey().trim();
                String value2= dataSnapshot.getValue().toString().trim();

                //CatgBudg.add(value1);
                //arrayAdapter1.notifyDataSetChanged();

                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("Cat", value1);
                datum.put("Bud", value2);
                data.add(datum);
                adapter.notifyDataSetChanged();

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


        catBudView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
            {
                Intent n = new Intent(getApplicationContext(), EditBud.class);
                HashMap<String, String> datum1 = new HashMap<String, String>(2);
                datum1=(HashMap<String,String>) adapter.getItem(pos);
                String result= datum1.get("Cat");
                n.putExtra("name", result);
                startActivity(n);
            }
        });
/*
        RefCat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value= dataSnapshot.getValue().toString().trim();
                Catg.add(value);
                arrayAdapter2.notifyDataSetChanged();
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
        });*/

    }
}
