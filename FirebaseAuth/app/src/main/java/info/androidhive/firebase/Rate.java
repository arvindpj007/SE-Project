package info.androidhive.firebase;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Rate extends Activity implements OnRatingBarChangeListener{

    RatingBar ratingbarClick;
    Button sub_btn;
    TextView textRatingView, textRatingViewSave;
    Boolean val = true;
    float ans = (float) 0.0;
    int count =0;
    float sum=(float)0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ratingbarClick = (RatingBar) findViewById(R.id.ratingBar);
        ratingbarClick.setOnRatingBarChangeListener(this);

        SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(Rate.this);
        sum = sharePref.getFloat("Get_Rating", 0.0f);
        count = sharePref.getInt("Get_Count",0);
        Toast.makeText(getApplicationContext(),"Ratings so far " + sum/count,Toast.LENGTH_LONG).show();
        if (val) {
            ratingbarClick.setRating(0);
        } else {
            ratingbarClick.setRating(ans);
        }



    }


    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


        ans = ratingbarClick.getRating();
        Toast.makeText(getApplicationContext(),"Rating: "+ans,Toast.LENGTH_LONG).show();
        sum=sum+ans;
        count=count+1;
        SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(Rate.this);
        SharedPreferences.Editor edit = sharePref.edit();
        edit.putFloat("Get_Rating", sum);
        edit.putInt("Get_Count",count);
        edit.commit();
        val = false;


    }

}