package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class EditBud extends AppCompatActivity {
    EditText updCatBudg;
    Button btupdbud;
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bud);

        Intent intent = getIntent();
        final String Catb = intent.getStringExtra("name");
        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("Categories");


        updCatBudg=findViewById(R.id.updCatBudg);
        btupdbud=findViewById(R.id.btupdbud);

        btupdbud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=updCatBudg.getText().toString().trim();
                RefCat.child(Catb).setValue(n);
                startActivity(new Intent(EditBud.this,SetBud.class));
            }
        });
    }
}
