package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class AddCat extends AppCompatActivity {
    Button AddCategory;
    EditText Cat,Bud;
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        AddCategory= findViewById(R.id.btAddCat);
        Cat= findViewById(R.id.CatName);
        Bud= findViewById(R.id.CatBudg);
        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("Categories");

        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String categ=Cat.getText().toString().trim();
                String catbudg=Bud.getText().toString().trim();
                RefCat.child(categ);
                RefCat.child(categ).setValue(catbudg);

                startActivity(new Intent(AddCat.this, ProfileManagement.class));
            }
        });


    }
}
