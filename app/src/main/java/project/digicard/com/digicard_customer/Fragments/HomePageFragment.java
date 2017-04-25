package project.digicard.com.digicard_customer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import project.digicard.com.digicard_customer.Adapters.ViewPagerAdapter;
import project.digicard.com.digicard_customer.CardEvent;
import project.digicard.com.digicard_customer.DialogList;
import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Network.RequestHandler;
import project.digicard.com.digicard_customer.QrScanner;
import project.digicard.com.digicard_customer.R;

import static project.digicard.com.digicard_customer.Model.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustid;

/**
 * Created by Sarvesh on 22-12-2016.
 */

public class HomePageFragment extends Fragment {
Button pay;
    TextView Logo,Balance,cardname;
    Button ChangeCard;
    JSONObject jsonObject;
    SharedPreferences sharedPreferences;
    String balance;
    AutoScrollViewPager mPager;
    private static final Integer[] IMAGES= {R.drawable.pizza,R.drawable.pizza2,R.drawable.pizza3};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.homepage_fragment, container, false);
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


cardname =(TextView)rootView.findViewById(R.id.cardname);
Balance = (TextView)rootView.findViewById(R.id.balance);
pay = (Button)rootView.findViewById(R.id.pay);
        Logo =(TextView)rootView.findViewById(R.id.logo);
        Typeface typeface= Typeface.createFromAsset(getActivity().getAssets(), "cursive.ttf");
        Logo.setTypeface(typeface);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),QrScanner.class));
            }
        });

        getCardData("100");

        ChangeCard = (Button)rootView.findViewById(R.id.changecard);

        ChangeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getActivity(),DialogList.class));
            }
        });

        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (AutoScrollViewPager)rootView. findViewById(R.id.pager);
        mPager.setAdapter(new ViewPagerAdapter(getActivity(),ImagesArray));
        mPager.setInterval(3000);
        mPager.startAutoScroll();

        return rootView;
    }










        @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy()
    {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
@Subscribe
    public void onEvent(CardEvent event){
        // your implementation
    cardname.setText(event.getCardname());
getCardData(event.getCardid().toString());
    }



    public void  getCardData(String cardid)
    {
        class  getCards extends AsyncTask<String,String,String>

        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                System.out.println("abc"+s);


            }

            @Override
            protected String doInBackground(String... params) {

                RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_id",params[0]);
                data.put("Card_id",params[1]);

                String result =  ruc.sendPostRequest(CONFIG.GET_CARDBALANCE,data);
                try {
                    jsonObject =new JSONObject(result);
                    JSONArray contacts = jsonObject.getJSONArray("result");
                    JSON_PARSE_DATA_AFTER_WEBCALL(contacts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return result ;
            }
        }

        getCards g = new getCards();
        g.execute(sharedPreferences.getString(sharedprefcustid,""),cardid);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {



            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                balance = json.getString("Balance");
                System.out.println("wow"+balance);






            } catch (JSONException e) {

                e.printStackTrace();
            }

        }




        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
Balance.setText(balance);
            }
        });

    }


}
