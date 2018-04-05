package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.collections4.map.MultiValueMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class AnalysisActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private Firebase mRootRef;
    private Firebase RefUid;
    private Firebase RefCatSum, RefTran,RefCat,OneRefCat;
    private List<Transaction> transList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransactionAdapter mAdapter;

    private ArrayList<String> Catg=new ArrayList<>();

    List<String>catList = new ArrayList<>();
    List<Integer>amtList = new ArrayList<>();

    CollapsingToolbarLayout collapsingToolbarLayout;
    int flagTime = -1;
    String SelCat;
    FirebaseAuth auth;
    Button press;
    ViewPager viewPage;
    LinearLayout sliderDots;
    int dotCount;
    private ImageView[] dots;
    Button pressButton;

    ArrayAdapter<CatTransSum> getSumCat;


    MultiValueMap<String, String> catgTrans = MultiValueMap.multiValueMap(new LinkedHashMap<String,Collection<String>>(),(Class<LinkedHashSet<String>>) (Class<?>)LinkedHashSet.class);
    int n;
    Integer amount[];
    String categories[];
    int amtcatIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Analysis");

        pressButton = (Button)findViewById(R.id.pressme);


        recyclerView = (RecyclerView) findViewById(R.id.rv_catanalysis);

        mAdapter = new TransactionAdapter(transList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        /*Transaction t =new Transaction("132","Bills","Elec","12/6/17");
        transList.add(t);
        mAdapter.notifyDataSetChanged();*/


        auth = FirebaseAuth.getInstance();

        mRootRef=new Firebase("https://expense-2a69a.firebaseio.com/");

        mRootRef.keepSynced(true);
        com.google.firebase.auth.FirebaseAuth auth = FirebaseAuth.getInstance();
        String Uid=auth.getUid();
        RefUid= mRootRef.child(Uid);
        RefCat=RefUid.child("CatTran");

        RefTran = RefUid.child("Transactions");
        RefCatSum=RefUid.child("CatSum");




        press=(Button) findViewById(R.id.pressme);
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i=new Intent(AnalysisActivity.this,GraphActivity.class);
               // startActivity(i);
            }
        });

        RefCatSum.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String cat=dataSnapshot.getKey();
                String amnt= dataSnapshot.getValue().toString().trim();
                catList.add(cat);
                amtList.add(Integer.parseInt(amnt));
                /*
                amount[amtcatIndex] = Integer.parseInt(amnt);
                categories[amtcatIndex] = cat;
                amtcatIndex++;*/
                //Toast.makeText(getApplicationContext(),cat+","+amnt,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        pressButton.setOnClickListener(this);

    }






    private void setUpPieChart() {

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i=0;i<amount.length;i++)
        {
            pieEntries.add(new PieEntry(amount[i],categories[i]));
        }

        PieDataSet pieDataSet=new PieDataSet(pieEntries, "AMOUNT SPEND - Categorywise");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData=new PieData(pieDataSet);

        PieChart chart=(PieChart) findViewById(R.id.pie_chart);
        chart.setData(pieData);
        chart.animateY(1000);
        chart.invalidate();




        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe=(PieEntry) e;
                SelCat=pe.getLabel();

                transList.clear();
                mAdapter.notifyDataSetChanged();
                OneRefCat=RefCat.child(SelCat);

                OneRefCat.addChildEventListener(new ChildEventListener() {
                String amount,cat,shname,shDay,shMonth,shYear;

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    int i=0;

                    for (DataSnapshot S:dataSnapshot.getChildren()) {
                        //String t_id=S.getValue().toString().trim();
                        //Toast.makeText(getApplicationContext(),"->"+i,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),t_id,Toast.LENGTH_SHORT).show();
                        switch(i)
                        {
                            case 0:
                                amount=S.getValue().toString().trim();
                                break;
                            case 1:
                                cat=S.getValue().toString().trim();
                                break;
                            case 2:
                                shDay=S.getValue().toString().trim();
                                break;
                            case 3:
                                shMonth=S.getValue().toString().trim();
                                break;
                            case 4:
                                shname=S.getValue().toString().trim();
                                break;
                            case 5:
                                shYear=S.getValue().toString().trim();
                                break;
                        }
                        //Transaction transaction=S.getValue(Transaction.class);
                        //transList.add(transaction);
                        i++;
                    }
                    String shdate= shDay+" - "+shMonth+" - "+shYear;
                    Transaction transaction=new Transaction(amount,cat,shname,shdate);
                    //Toast.makeText(getApplicationContext(),transaction.getT_amt(),Toast.LENGTH_SHORT).show();
                    transList.add(transaction);
                    mAdapter.notifyDataSetChanged();

                }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


            }

            @Override
            public void onNothingSelected() {

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onClick(View v) {

        amount = amtList.toArray(new Integer[amtList.size()]);
        categories = catList.toArray(new String[catList.size()]);
        setUpPieChart();

    }
}
