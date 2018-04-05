package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class OneCatTran extends AppCompatActivity {
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat,OneRefCat;
    private List<Transaction> transList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransactionAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_cat_tran);

        Intent intent = getIntent();
        String OneTcat = intent.getStringExtra("name");
        //Toast.makeText(this,OneTcat,Toast.LENGTH_LONG).show();

        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("CatTran");
        OneRefCat=RefCat.child(OneTcat);



        recyclerView = (RecyclerView) findViewById(R.id.rv_onecattrans);

        mAdapter = new TransactionAdapter(transList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        OneRefCat.addChildEventListener(new ChildEventListener() {
            String amount,cat,shname,shDay,shMonth,shYear;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int i=0;

                for (DataSnapshot S:dataSnapshot.getChildren()) {
                    //String t_id=S.getValue().toString().trim();
                    //Toast.makeText(getApplicationContext(),"->"+i,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),t_id,Toast.LENGTH_SHORT).show();
                    switch(i)
                    {
                        case 0:
                            amount=S.getValue().toString().trim();
                            break;
                        case 1:
                            cat=S.getValue().toString().trim();
                            break;
                        case 2:
                            shDay=S.getValue().toString().trim();
                            break;
                        case 3:
                            shMonth=S.getValue().toString().trim();
                            break;
                        case 4:
                            shname=S.getValue().toString().trim();
                            break;
                        case 5:
                            shYear=S.getValue().toString().trim();
                            break;
                    }
                    //Transaction transaction=S.getValue(Transaction.class);
                    //transList.add(transaction);
                    i++;
                }
                String shdate= shDay+" - "+shMonth+" - "+shYear;
                Transaction transaction=new Transaction(amount,cat,shname,shdate);
                //Toast.makeText(getApplicationContext(),transaction.getT_amt(),Toast.LENGTH_SHORT).show();
                transList.add(transaction);
                mAdapter.notifyDataSetChanged();
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





    }
}
