package com.example.android.xpenseauditor;

/**
 * Created by apj on 22/02/18.
 */

public class Transaction {

    private String t_id,t_shopname,t_price,t_cat,t_date;

    public Transaction()
    {}

    public Transaction(String t_id,String t_shopname,String t_price,String t_cat,String t_date)
    {
        this.t_id=t_id;
        this.t_shopname=t_shopname;
        this.t_price=t_price;
        this.t_cat=t_cat;
        this.t_date=t_date;
    }

    public String getT_id() {
        return t_id;
    }

    public String getT_shopname() {
        return t_shopname;
    }

    public String getT_category() {
        return t_cat;
    }

    public String getT_price() {
        return t_price;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public void setT_shopname(String t_shopname) {
        this.t_shopname = t_shopname;
    }

    public void setT_price(String t_price) {
        this.t_price = t_price;
    }

    public void setT_cat(String t_cat) {
        this.t_cat = t_cat;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

}

