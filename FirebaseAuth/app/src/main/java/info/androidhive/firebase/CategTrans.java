package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CategTrans extends AppCompatActivity {
    private ListView catView;
    private ArrayList<String> Catg=new ArrayList<>();
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categ_trans);


        catView = (ListView) findViewById((R.id.TCatList));
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Catg);
        catView.setAdapter(arrayAdapter);

        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("CatTran");


        RefCat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value= dataSnapshot.getKey().trim();
                Catg.add(value);
                arrayAdapter.notifyDataSetChanged();
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





        catView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0,View arg1, int pos, long arg3)
            {

                Intent n = new Intent(getApplicationContext(), OneCatTran.class);
                String data=(String)arg0.getItemAtPosition(pos);
                //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG);
                n.putExtra("name", data);
                startActivity(n);
            }
        });



    }
}
