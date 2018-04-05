package info.androidhive.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.provider.CalendarContract.CalendarCache.URI;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    com.google.firebase.auth.FirebaseAuth auth;
    private TextView NameView, EmailView, PhnView, UserDOB, UserAddress;
    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefName, RefEmail, RefPhnnum, RefAddress, RefDay, RefMonth, RefYear;
    private ImageButton editProf;
    private String day, month, year;
    public static Uri downloadUrl;
    private static final int galleryReq = 1;
    StorageReference storageReference, filepath,storageRef;
    ImageButton changePic;
    ImageView userImage;
    Uri imageUri = null;
    String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NameView = (TextView) findViewById(R.id.userName);
        EmailView = (TextView) findViewById(R.id.userEmail);
        PhnView = (TextView) findViewById(R.id.userPhone);
        UserAddress = (TextView) findViewById(R.id.userAddress);
        UserDOB = (TextView) findViewById(R.id.userDOB);
        editProf = (ImageButton) findViewById(R.id.editProfile);

        auth= FirebaseAuth.getInstance();


        mRootRef = new Firebase("https://expense-2a69a.firebaseio.com/");
        mRootRef.keepSynced(true);
        Uid = auth.getUid();
        RefUid = mRootRef.child(Uid);
        RefName = RefUid.child("Name");
        RefEmail = RefUid.child("Email");
        RefPhnnum = RefUid.child("Phone Number");
        RefAddress = RefUid.child("Address");
        RefDay = RefUid.child("Day");
        RefMonth = RefUid.child("Month");
        RefYear = RefUid.child("Year");

        storageReference = FirebaseStorage.getInstance().getReference();

        userImage = (ImageView) findViewById(R.id.userImage);



        storageRef=storageReference.child("Profile Image").child(Uid+".jpg");
        /*storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imuri=uri;
                //Handle whatever you're going to do with the URL here
            }
        });
*/


        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    userImage.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}


        //userImage.setImageURI(storageReference.child("Profile Image").child(Uid+".jpg").getDownloadUrl().getResult());
       /* try {
            final File localFile = File.createTempFile("images", "jpg");
            storageReference.child("Profile Image").child(Uid).getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                    userImage.setImageURI((android.net.Uri) localFile.toURI());
                }
            });

        }catch(Exception e){

        }*/


        changePic = (ImageButton) findViewById(R.id.changePic);
        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (view.getId() == R.id.changePic) {*/
                    Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                    gallery.setType("image/*");
                    startActivityForResult(gallery, galleryReq);
                 /*else if (view.getId() == R.id.editProfile) {
                    Intent i = new Intent(ProfileActivity.this, EditProfile.class);
                    startActivity(i);
                }*/
            }


        });


        RefName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                String n = DS.getValue(String.class);
                NameView.setText(n);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RefEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                String n = DS.getValue().toString().trim();

                EmailView.setText(n);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefPhnnum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                String n = DS.getValue().toString().trim();

                PhnView.setText(n);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RefAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                String n = DS.getValue().toString().trim();

                UserAddress.setText(n);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        RefDay.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                day = DS.getValue().toString().trim();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RefMonth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                month = DS.getValue().toString().trim();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RefYear.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot DS) {
                year = DS.getValue().toString().trim();
                UserDOB.setText(day + "/" + month + "/" + year);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfile.class);
                i.putExtra("NAME", NameView.getText().toString().trim());
                i.putExtra("EMAIL", EmailView.getText().toString().trim());
                i.putExtra("PHONE", PhnView.getText().toString().trim());
                i.putExtra("ADDRESS", UserAddress.getText().toString().trim());
                i.putExtra("DAY", day);
                i.putExtra("MONTH", month);
                i.putExtra("YEAR", year);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

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
        //noinspection SimplifiableIfStatement
        if (id == R.id.account_settings) {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else if(id== R.id.action_settings)
        {
            Intent i=new Intent(this,PrefSettingsActivity.class);
            startActivity(i);
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

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "I recommend you to try this app and comment about it";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "XpensAuditor");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryReq && resultCode == RESULT_OK) {
            imageUri = data.getData();
            userImage.setImageURI(imageUri);
            filepath = storageReference.child("Profile Image").child(Uid+".jpg");
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUrl = taskSnapshot.getDownloadUrl();
                    //Toast.makeText(getApplicationContext(),downloadUrl.toString(),Toast.LENGTH_LONG).show();

                }
            });


        }
    }
}