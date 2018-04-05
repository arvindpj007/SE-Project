package info.androidhive.firebase;

import android.app.Application;
import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseAuth extends Application {

    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

    }

}
