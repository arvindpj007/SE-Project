package info.androidhive.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    info.androidhive.firebase.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    com.google.firebase.auth.FirebaseAuth auth;

    ImageView userImage;

    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefName,RefEmail;
    TextView tvHeaderName, tvHeaderMail;
    StorageReference storageReference, filepath,storageRef;
    Uri imageUri = null;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");
        mRootRef.keepSynced(true);
        auth = com.google.firebase.auth.FirebaseAuth.getInstance();
        Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefName = RefUid.child("Name");
        RefEmail=RefUid.child("Email");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderView =  navigationView.getHeaderView(0);
        tvHeaderName = (TextView)navHeaderView.findViewById(R.id.headerName);
        tvHeaderMail = (TextView)navHeaderView.findViewById(R.id.headerEmail);
        userImage = (ImageView)navHeaderView.findViewById(R.id.imageView);
        storageReference = FirebaseStorage.getInstance().getReference();
        storageRef=storageReference.child("Profile Image").child(Uid+".jpg");
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


        navigationView.setNavigationItemSelectedListener(this);


        RefName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvHeaderName.setText(dataSnapshot.getValue().toString().trim());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        RefEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvHeaderMail.setText(dataSnapshot.getValue().toString().trim());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new info.androidhive.firebase.ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                String check = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                if(check.equals("Current List of Categories"))
                {
                    Intent i = new Intent(SettingsActivity.this,CategoryList.class);
                    startActivity(i);
                }

                else if(check.equals("% expense before alert"))
                {

                    Intent i = new Intent(SettingsActivity.this,PercentExpense.class);
                    startActivity(i);

                }

                else if(check.equals("Add new category"))
                {
                    Intent i = new Intent(SettingsActivity.this,AddCat.class);
                    startActivity(i);
                }

                else if(check.equals("Common Shops/category"))
                {

                }

                else if(check.equals("Bank"))
                {

                }

                else if(check.equals("Paytm"))
                {

                }

                else if(check.equals("Tez"))
                {

                }

                else if(check.equals("Shop"))
                {

                }

                else if(check.equals("Restaurants"))
                {

                }
                return false;

            }
        });


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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Category");
        listDataHeader.add("Message pattern");

        // Adding child data
        List<String> category = new ArrayList<String>();
        category.add("Current List of Categories");
        category.add("% expense before alert");
        category.add("Add new category");
        category.add("Common Shops/category");

        List<String> patterns = new ArrayList<String>();
        patterns.add("Banks");
        patterns.add("Paytm");
        patterns.add("Tez");
        patterns.add("Shops");
        patterns.add("Restaurants");

        listDataChild.put(listDataHeader.get(0), category); // Header, Child data
        listDataChild.put(listDataHeader.get(1), patterns);
    }

}
