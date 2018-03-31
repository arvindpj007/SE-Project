package com.example.android.xpenseauditor;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class SMSActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    String OTP_REGEX = "[0-9]{1,6}";
    Button b;
    TextView lblMsg, lblNo;
    ListView lvMsg;
    double amt;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //Request Permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_SMS))
            {

            }

            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},0);
            }
        }

        lvMsg = (ListView)findViewById(R.id.lvMsg);

        b = (Button)findViewById(R.id.btnInbox);
        b.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        b.setVisibility(View.INVISIBLE);
        Uri inboxURI = Uri.parse("content://sms/inbox");

        // List required columns
        String[] reqCols = new String[] { "_id", "address", "date", "body" };

        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = getContentResolver();

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor c = cr.query(inboxURI, reqCols, reqCols[1]+" LIKE ?", new String[]{"%"+ "" +"%"}, null);

        // Attached Cursor with adapter and display in listview
        adapter = new SimpleCursorAdapter(this, R.layout.row, c, new String[] { "body", "address" }, new int[] {R.id.lblMsg, R.id.lblNumber });
        lvMsg.setAdapter(adapter);
        lvMsg.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        int flag=0;
        String id = String.valueOf(adapterView.getItemIdAtPosition(i));
        Cursor object= (Cursor) adapterView.getItemAtPosition(i);
        String body=object.getString(3);
        String date=object.getString(2);
        Long timestamp = Long.parseLong(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        Date finaldate = calendar.getTime();
        String smsDate = finaldate.toString();

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("MESSAGE:");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {} });
        adb.setMessage(body+'\n'+smsDate);
        String[] words = body.split(" ");
        for(int k=0;k<words.length;k++)
        {
            if(words[k].equalsIgnoreCase("debited"))
            {
                flag = 1;
                break;
            }
        }
        String word="";

        if(flag==1)
        {
            for(int k=0;k<words.length;k++)
            {
                if(words[k].contains("Rs."))
                {
                    word=words[k].replaceAll(",","");
                    amt = Double.parseDouble(word.substring(3));
                    break;
                }
                else if(words[k].equalsIgnoreCase("INR")||words[k].equalsIgnoreCase("Rs"))
                {
                    word=words[k+1].replaceAll(",","");
                    amt = Double.parseDouble(word);
                    break;
                }


            }

            Toast.makeText(this,"Amount:"+ amt,Toast.LENGTH_SHORT).show();

        }

        AlertDialog ad=adb.create();
        ad.show();

    }
}


