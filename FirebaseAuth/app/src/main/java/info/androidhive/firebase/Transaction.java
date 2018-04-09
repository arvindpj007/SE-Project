package info.androidhive.firebase;

public class Transaction {

    private String tid, t_shopname, t_amt,t_cat,t_date, t_msg;

    public Transaction() {
    }

    public Transaction(Transaction obj){
        this.tid = obj.tid;
        this.t_amt = obj.t_amt;
        this.t_shopname = obj.t_shopname;
        this.t_cat = obj.t_cat;
        this.t_date = obj.t_date;
        this.t_msg = obj.t_msg;
    }
    public Transaction( String tid, String t_amt, String t_cat,String t_shopname,String t_date, String t_msg) {
        this.tid = tid;
        this.t_amt=t_amt;
        this.t_cat=t_cat;
        this.t_shopname=t_shopname;
        this.t_date=t_date;
        this.t_msg = t_msg;
    }

    public String getTid() {
        return tid;
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

    public String getT_msg() {
        return t_msg;
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

    public void setT_msg(String t_msg) {
        this.t_msg = t_msg;
    }

}
