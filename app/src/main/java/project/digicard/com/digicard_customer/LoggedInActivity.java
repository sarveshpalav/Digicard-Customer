package project.digicard.com.digicard_customer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import project.digicard.com.digicard_customer.Adapters.TabsPagerAdapterLoggedIn;

/**
 * Created by Sarvesh on 21-12-2016.
 */

public class LoggedInActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsPagerAdapterLoggedIn adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        adapter = new TabsPagerAdapterLoggedIn(getSupportFragmentManager());

        tabLayout = (TabLayout)findViewById(R.id.loggedintab_layout);
        viewPager =(ViewPager)findViewById(R.id.loggedinviewpager);

        tabLayout.setTabTextColors(R.color.white,R.color.white);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        subscribeToPushService();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("global");

        Log.d("AndroidBash", "Notifications Enabled");
        Toast.makeText(LoggedInActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        //  Log.d("AndroidBash", token);
        //   Toast.makeText(Home.this, token, Toast.LENGTH_SHORT).show();
    }

}
