package info.androidhive.firebase;

public class Transaction {

    private String t_shopname, t_amt,t_cat,t_date;

    public Transaction() {
    }

    public Transaction( String t_amt, String t_cat,String t_shopname,String t_date) {
        this.t_amt=t_amt;
        this.t_cat=t_cat;
        this.t_shopname=t_shopname;
        this.t_date=t_date;
    }

    public String getT_amt() {
        return t_amt;
    }


    public String getT_cat() {
        return t_cat;
    }

    public String getT_shopname() {
        return t_shopname;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public void setT_amt(String t_amt) {
        this.t_amt = t_amt;
    }

    public void setT_cat(String t_cat) {
        this.t_cat = t_cat;
    }

    public void setT_shopname(String t_shopname) {
        this.t_shopname = t_shopname;
    }

}
