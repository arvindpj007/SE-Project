package com.example.android.xpenseauditor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by apj on 20/02/18.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>
{

    static TabFragment ctx;
    public Context context;
    private List<Transaction> trans_list;

    public TransactionAdapter(List<Transaction> list, TabFragment context){
        trans_list = list;
        this.ctx = context;
    }

    TransactionAdapter(){

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        public TextView id, shopName, price, category, date;

        public ViewHolder(View v){
            super(v);
            id = (TextView)v.findViewById(R.id.t_id);
            shopName = (TextView)v.findViewById(R.id.t_shop);
            price = (TextView)v.findViewById(R.id.t_price);
            category = (TextView)v.findViewById(R.id.t_category);
            date = (TextView)v.findViewById(R.id.t_date);

            v.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem changeCategory = menu.add(Menu.NONE,1,1,"Change Category");
            MenuItem delTrans = menu.add(Menu.NONE,2,2,"Delete Transaction");
            MenuItem showMsg = menu.add(Menu.NONE,3,3,"Show Message");

            changeCategory.setOnMenuItemClickListener(onContentMenu);
            delTrans.setOnMenuItemClickListener(onContentMenu);
            showMsg.setOnMenuItemClickListener(onContentMenu);
        }

        private final MenuItem.OnMenuItemClickListener onContentMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Implement actions on clicking
                switch (item.getItemId()){
                    case 1:
                    {
                        Intent i = new Intent(ctx.getActivity(),ChangeCategory.class);
                        ctx.getActivity().startActivity(i);
                    }break;

                    case 2:
                    {

                    }break;

                    case 3:
                    {

                    }break;
                }
                return true;
            }
        };


    }

    public List<Transaction> getData()
    {
        return trans_list;
    }

    public long getItemId(int position){
        return super.getItemId(position);
    }

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_row,parent,false);
        TransactionAdapter.ViewHolder vh = new TransactionAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(trans_list.get(position).getT_id());
        holder.category.setText(trans_list.get(position).getT_category());
        holder.price.setText(trans_list.get(position).getT_price());
        holder.shopName.setText(trans_list.get(position).getT_shopname());
        holder.date.setText(trans_list.get(position).getT_date());
    }

    @Override
    public int getItemCount() {
        return trans_list.size();
    }

    private int position;

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }
}

