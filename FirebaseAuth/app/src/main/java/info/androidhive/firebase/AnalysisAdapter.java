package info.androidhive.firebase;

/**
 * Created by user on 09-04-2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.MyViewHolder> {

    private List<Transaction> transList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alltrans_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Transaction trans = transList.get(position);
        holder.tid.setText(trans.getTid());
        holder.tcat.setText(trans.getT_cat());
        holder.tamt.setText(trans.getT_amt());
        holder.tshopname.setText(trans.getT_shopname());
        holder.tdate.setText(trans.getT_date());

    }

    @Override
    public int getItemCount() {
        return transList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tid, tamt, tcat, tshopname, tdate;


        public MyViewHolder(View view) {
            super(view);
            tid = (TextView) view.findViewById(R.id.tid);
            tamt = (TextView) view.findViewById(R.id.tamt);
            tcat = (TextView) view.findViewById(R.id.tcat);
            tshopname = (TextView) view.findViewById(R.id.tshopname);
            tdate = (TextView) view.findViewById(R.id.tdate);

        }


    }

    public AnalysisAdapter(List<Transaction> transList) {
        this.transList = transList;
    }
}
