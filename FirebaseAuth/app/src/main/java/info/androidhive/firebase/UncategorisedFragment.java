package info.androidhive.firebase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UncategorisedFragment extends Fragment {


    private Firebase mRootRef;
    private Firebase RefUid,RefTran;
    private Firebase RefCat;
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
    public UncategorisedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uncategorised, container, false);
    }

}
