package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class SignSetCatBudget extends AppCompatActivity {
    EditText FoodBud,BillsBud,EducationBud,HealthBud,HomeNeedsBud,Otherbud,TravelBud;
    Button SetBud;
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCat,RefFood,RefHealth,RefTravel,RefEdu,RefBills,RefHomeNeeds,RefOthers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_set_cat_budget);
        FoodBud=findViewById(R.id.FoodBud);
        BillsBud=findViewById(R.id.BillsBud);
        EducationBud=findViewById(R.id.EducationBud);
        HealthBud=findViewById(R.id.HealthBud);
        HomeNeedsBud=findViewById(R.id.HomeNeedsBud);
        Otherbud=findViewById(R.id.OtherBud);
        TravelBud=findViewById(R.id.TravelBud);
        SetBud=findViewById(R.id.SetBud);

        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        final com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("Categories");

        RefFood=RefCat.child("Food");

        RefHealth=RefCat.child("Health");

        RefTravel=RefCat.child("Travel");

        RefEdu=RefCat.child("Education");

        RefBills=RefCat.child("Bills");

        RefHomeNeeds=RefCat.child("Home Needs");

        RefOthers=RefCat.child("Others");



        SetBud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food=FoodBud.getText().toString().trim();
                String bills=BillsBud.getText().toString().trim();
                String health=HealthBud.getText().toString().trim();
                String travel=TravelBud.getText().toString().trim();
                String education=EducationBud.getText().toString().trim();
                String homeneeds=HomeNeedsBud.getText().toString().trim();
                String other=Otherbud.getText().toString().trim();
                if(!food.isEmpty() && !bills.isEmpty() && !health.isEmpty() && !travel.isEmpty() && !education.isEmpty() && !homeneeds.isEmpty() && !other.isEmpty()) {
                    RefFood.setValue(food);
                    RefHealth.setValue(bills);
                    RefTravel.setValue(health);
                    RefEdu.setValue(travel);
                    RefBills.setValue(education);
                    RefHomeNeeds.setValue(homeneeds);
                    RefOthers.setValue(other);
                    auth.signOut();
                    startActivity(new Intent(SignSetCatBudget.this, setpw.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Set all the values",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
