package com.example.android.xpenseauditor;

import android.app.Application;
import android.app.Application;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by apj on 13/03/18.
 */

public class XpenseAuditor extends Application {

    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

    }
}
