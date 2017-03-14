package project.digicard.com.digicard_customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import static project.digicard.com.digicard_customer.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.CONFIG.sharedprfisloggedin;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
private TabsPagerAdapter adapter;
  private   SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean(sharedprfisloggedin,false))
        {
            startActivity(new Intent(this,LoggedInActivity.class));
            finish();
        }

        adapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager =(ViewPager)findViewById(R.id.view_pager);


viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
}
