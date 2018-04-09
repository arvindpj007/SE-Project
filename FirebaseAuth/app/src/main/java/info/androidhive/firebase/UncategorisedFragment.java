package info.androidhive.firebase;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UncategorisedFragment extends Fragment {

    private String tagId, catChangeTo ;
    private Firebase mRootRef;
    private Firebase RefUid,RefTran,RefCat;
    int pos, intSum;
    private TextView textView;
    private ArrayList<String> Catg=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ListView changeCat;
    private Context context;
    private List<Transaction> TransactionListUF = new ArrayList<>();
    private RecyclerView recyclerViewUF;
    private TransactionAdapter mAdapterUF;


    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos2", position);
        UncategorisedFragment uncategorisedFragment = new UncategorisedFragment();
        uncategorisedFragment.setArguments(bundle);
        return uncategorisedFragment;
    }
    public UncategorisedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uncategorised, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toast.makeText(view.getContext(),"position: "+position,Toast.LENGTH_SHORT).show();


        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefTran = RefUid.child("UnCatTran");
        RefCat=RefUid.child("Categories");

        arrayAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,Catg);

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


        recyclerViewUF = (RecyclerView) view.findViewById(R.id.rv_uncat);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewUF.setLayoutManager(mLayoutManager);
        recyclerViewUF.setItemAnimator(new DefaultItemAnimator());
        mAdapterUF = new TransactionAdapter(TransactionListUF);
        recyclerViewUF.setAdapter(mAdapterUF);
        prepareTransactionData();
        registerForContextMenu(recyclerViewUF);


        mAdapterUF.setOnItemClickListener(new TransactionAdapter.ClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                //Toast.makeText(getActivity(),TransactionList.get(position).getTid(),Toast.LENGTH_SHORT).show();
            try {
                Intent i = new Intent(getActivity(), SMSTransShow.class);
                i.putExtra("indexPos", TransactionListUF.get(position).getTid());
                startActivity(i);
            }catch (Exception e){

            }
            }

            @Override
            public void OnItemLongClick(int position, View v) {
                Log.i("yoyoyo","yoyoyooyoyoyoyo");
                pos=position;
            }
        });




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case 21:{
                int show = item.getGroupId();
                tagId=TransactionListUF.get(show).getTid();
                //Toast.makeText(getActivity(),tagId+"-"+"Delete it",Toast.LENGTH_SHORT).show();


                RefTran.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String checkID = dataSnapshot.getKey().toString().trim();
                        if(tagId.equals(checkID)) {
                            dataSnapshot.getRef().removeValue(new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    //Toast.makeText(getActivity(), tagId + "-Deleted", Toast.LENGTH_LONG).show();
                                    TransactionListUF.clear();
                                    prepareTransactionData();

                                }
                            });
                        }

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
            }break;

            case 22:{
                int show = item.getGroupId();
                tagId=TransactionListUF.get(show).getTid();
                final Transaction updateTransac = new Transaction(TransactionListUF.get(show)); //Transaction to be modified

                //Toast.makeText(getActivity(),tagId+"-"+"Change it",Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Title...");

                changeCat= (ListView) dialog.findViewById(R.id.CatgList);
                changeCat.setAdapter(arrayAdapter);
                dialog.show();
                changeCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        catChangeTo = adapterView.getItemAtPosition(i).toString().trim();
                        Toast.makeText(getActivity(),catChangeTo,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        //Deleting from everywhere
                        RefTran.child(tagId).removeValue();

                        //Adding to the new category
                        String tempDate = updateTransac.getT_date();
                        String[] dateSet = tempDate.split(" - ");
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("Amount").setValue(updateTransac.getT_amt());
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("Category").setValue(catChangeTo);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("Day").setValue(dateSet[0]);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("Month").setValue(dateSet[1]);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("Shop Name").setValue(updateTransac.getT_shopname());
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("Year").setValue(dateSet[2]);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("Transactions").child(tagId).child("ZMessage").setValue(updateTransac.getT_msg());

                        //Adding to CatTran
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("Amount").setValue(updateTransac.getT_amt());
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("Category").setValue(catChangeTo);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("Day").setValue(dateSet[0]);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("Month").setValue(dateSet[1]);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("Shop Name").setValue(updateTransac.getT_shopname());
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("Year").setValue(dateSet[2]);
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatTran").child(catChangeTo).child(tagId).child("ZMessage").setValue(updateTransac.getT_msg());

                        //Changing CatSum
                        RefUid.child("DateRange").child(dateSet[1]+"-"+dateSet[2]).child("CatSum").child(catChangeTo).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String sumCat = dataSnapshot.getValue().toString().trim();
                                intSum = Integer.parseInt(sumCat);
                                Integer newDelAmt = Integer.parseInt(updateTransac.getT_amt());
                                intSum = intSum + newDelAmt;
                                if(intSum==0)
                                    dataSnapshot.getRef().removeValue();
                                else
                                    dataSnapshot.getRef().setValue(String.valueOf(intSum));
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }

                        });

                    }
                });

            }break;

        }
        return super.onContextItemSelected(item);
    }


    private void prepareTransactionData() {
        RefTran.addChildEventListener(new ChildEventListener() {
            String amount,cat,shname,shDay,shMonth,shYear,shMsg;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int i=0;

                String tid = dataSnapshot.getKey().toString().trim();
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
                        case 6:
                            shMsg=S.getValue().toString().trim();
                            break;


                    }
                    //Transaction transaction=S.getValue(Transaction.class);
                    //transList.add(transaction);
                    i++;
                }
                String shdate= shDay+" - "+shMonth+" - "+shYear;
                Transaction transaction=new Transaction(tid,amount,cat,shname,shdate,shMsg);
                //Toast.makeText(getApplicationContext(),transaction.getT_amt(),Toast.LENGTH_SHORT).show();
                TransactionListUF.add(transaction);
                mAdapterUF.notifyDataSetChanged();
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



