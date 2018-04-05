package info.androidhive.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    String smsMessagestr, shopName="";
    int flag, preflag, reCheck;
    private Firebase mRootRef;
    private Firebase RefUid,RefTran,UnCatTran;
    private String Tid ;
    Double amt;

    @Override
    public void onReceive(Context context, Intent intent) {

/*
        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefTran = RefUid.child("Transactions");
        UnCatTran = RefUid.child("UnCatTran");
*/

        Bundle intentExtras = intent.getExtras();
        if(intentExtras!=null){
            Object[] sms = (Object[])intentExtras.get(SMS_BUNDLE);
            smsMessagestr="";
            for(int i=0;i<sms.length;i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])sms[i]);
                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessagestr += "SMS From: "+address+"\n";
                smsMessagestr += smsBody+"\n";

            }

            String[] words = smsMessagestr.split(" ");
            for(int k=0; k<words.length;k++){
               if(words[k].equalsIgnoreCase("SBI")){
                   flag=1;
                   break;
               }

            }

            String word="";
            if(flag==1) {
                for (int k = 0; k < words.length; k++) {
                    if (words[k].contains("Rs")) {
                        word = words[k].replaceAll(",", "");
                        amt = Double.parseDouble(word.substring(2));
                        break;
                    }
                }


                Calendar calendar = Calendar.getInstance();
                int day = (calendar.get(Calendar.DAY_OF_MONTH));
                int month = (calendar.get(Calendar.MONTH)+1);
                int year = (calendar.get(Calendar.YEAR));
                SimpleDateFormat mdFormat = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = "Current date: " + mdFormat.format(calendar.getTime());

                int k;

                for (k = 1; k < words.length; k++) {
                    if (words[k - 1].equalsIgnoreCase("at") || words[k - 1].equalsIgnoreCase("@")) {
                        preflag = 1;
                        reCheck = k;

                    }

                    for (; reCheck < words.length; reCheck++) {
                        if (words[reCheck].equalsIgnoreCase("txn#"))
                            break;
                    }


                    if (preflag == 1)
                        break;
                }
                for (int m = k; m < reCheck; m++)
                    shopName += words[m];
                Toast.makeText(context, "Amount:" + amt + "Shop Name:" + shopName + day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();


/*
                Tid = UUID.randomUUID().toString();
                RefTran.child(Tid);
                RefTran.child(Tid).child("Shop Name");
                RefTran.child(Tid).child("Amount");
                RefTran.child(Tid).child("Category");
                RefTran.child(Tid).child("Amount").setValue(amt);
                RefTran.child(Tid).child("Category").setValue("Uncategorised");
                RefTran.child(Tid).child("Shop Name").setValue(shopName);
                RefTran.child(Tid).child("Day").setValue(day);
                RefTran.child(Tid).child("Month").setValue(month);
                RefTran.child(Tid).child("Year").setValue(year);
                UnCatTran.child(Tid).child("Amount").setValue(amt);
                UnCatTran.child(Tid).child("Category").setValue("Uncategorised");
                UnCatTran.child(Tid).child("Shop Name").setValue(shopName);
                UnCatTran.child(Tid).child("Day").setValue(day);
                UnCatTran.child(Tid).child("Month").setValue(month);
                UnCatTran.child(Tid).child("Year").setValue(year);
                */
            }

        }
    }
}
