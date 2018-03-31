package com.example.android.xpenseauditor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apj on 23/02/18.
 */

public class TabFragment extends Fragment {

    int position;
    private TextView textView;

    private List<Transaction> TransactionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransactionAdapter mAdapter;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toast.makeText(view.getContext(),"position: "+position,Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TransactionAdapter(TransactionList,this);
        recyclerView.setAdapter(mAdapter);
        prepareTransactionData();

    }

    private void prepareTransactionData() {

        Transaction transaction = new Transaction("122", "Amazon", "2015","12-1-19","business");
        TransactionList.add(transaction);

        transaction = new Transaction("123", "Flipkart", "1223","12-1-19","Travel");
        TransactionList.add(transaction);

        mAdapter.notifyDataSetChanged();
    }


}
