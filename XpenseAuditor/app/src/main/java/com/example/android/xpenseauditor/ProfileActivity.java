package com.example.android.xpenseauditor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private DatabaseReference mRootRef;
    private DatabaseReference RefUid,RefDetails;


    public static Uri downloadUrl;
    private static final int galleryReq = 1;
    StorageReference storageReference, filepath;
    ImageButton changePic;
    ImageView userImage, edit;
    TextView userName, userPhone, userEmail, dob;
    Uri imageUri = null;
    public DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        auth = FirebaseAuth.getInstance();

        mRootRef=FirebaseDatabase.getInstance().getReference();
        RefUid=mRootRef.child(auth.getCurrentUser().getUid());
        RefDetails =RefUid.child("Details");
        userEmail = (TextView)findViewById(R.id.userEmail);
        userName = (TextView)findViewById(R.id.userName);
        userPhone = (TextView)findViewById(R.id.userPhone);
        dob = (TextView)findViewById(R.id.userDOB);

        userImage = (ImageView) findViewById(R.id.userImage);

        changePic = (ImageButton) findViewById(R.id.changePic);
        edit = (ImageButton) findViewById(R.id.editProfile);
        changePic.setOnClickListener(this);
        edit.setOnClickListener(this);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        //String user= String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //Toast.makeText(this, user+":Key ", Toast.LENGTH_SHORT).show();
        //fetchData(user);

        Toast.makeText(getApplicationContext(),RefDetails.getKey().toString().trim(),Toast.LENGTH_SHORT).show();

        RefDetails.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                int i=0;
                for (com.google.firebase.database.DataSnapshot S: dataSnapshot.getChildren()
                     ) {

                    Toast.makeText(getApplicationContext(),i,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),S.getValue().toString().trim(),Toast.LENGTH_SHORT).show();

                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





/*
        RefName.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot DS) {
                String n=DS.getValue().toString().trim();
                userName.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        RefEmail.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot DS) {
                String n=DS.getValue().toString().trim();
                userEmail.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        RefPhnnum.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot DS) {
                String n=DS.getValue().toString().trim();
                userPhone.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        RefName.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue().toString().trim();
                userName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        RefEmail.addValueEventListener(this);
        RefPhnnum.addValueEventListener(this);
*/
    }
/*
    public void fetchData(@NonNull String tag){
        final DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child(tag);
        dR.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Value is",value);
           }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           /* Intent i=new Intent(this,PrefSettings.class);
            startActivity(i);*/
        }

        else if(id==R.id.action_contact_us){
            Intent i=new Intent(this,ContactUs.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent i=new Intent(this,HomeActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_profile) {

            Intent i=new Intent(this,ProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_show_analysis) {

            Intent i=new Intent(this,AnalysisActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_settings) {

            Intent i=new Intent(this,SettingsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {

            auth.signOut();
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_rate) {

            Intent i=new Intent(this,Rate.class);
            startActivity(i);

        } else if (id == R.id.nav_suggest) {

            Intent i=new Intent(this,Suggest.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.changePic) {
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            startActivityForResult(gallery, galleryReq);
        } else if (view.getId() == R.id.editProfile) {
            Intent i = new Intent(this, EditProfile.class);
            startActivity(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryReq && resultCode == RESULT_OK) {
            imageUri = data.getData();
            userImage.setImageURI(imageUri);
            filepath = storageReference.child("Profile Image").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }


    }


}