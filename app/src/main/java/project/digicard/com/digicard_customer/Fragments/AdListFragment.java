package project.digicard.com.digicard_customer.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.digicard.com.digicard_customer.Adapters.AdsRecylerviewAdapter;
import project.digicard.com.digicard_customer.Model.Adsdata;
import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Network.RequestHandler;
import project.digicard.com.digicard_customer.R;

/**
 * Created by sarveshpalav on 10/02/17.
 */
public class AdListFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar loading;
    TextView Fetch;
    JSONObject jsonObject;
    List<Adsdata> GetDataAdapter1;
    RecyclerView.LayoutManager recyclerViewlayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GetDataAdapter1 = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fargment_ads, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.adsrecylerview);
        loading = (ProgressBar) rootView.findViewById(R.id.progressBarCard);
        Fetch = (TextView) rootView.findViewById(R.id.fetchingads);


        loading.setVisibility(View.INVISIBLE);
        Fetch.setVisibility(View.INVISIBLE);
        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());


        recyclerView.setLayoutManager(recyclerViewlayoutManager);


        getCards();


        return rootView;

    }

    public void  getCards()
    {
    class getAds extends AsyncTask<String, String, String>

    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
            Fetch.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("bcd" + s);
            loading.setVisibility(View.INVISIBLE);
            Fetch.setVisibility(View.INVISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {

            RequestHandler ruc = new RequestHandler();

            HashMap<String, String> data = new HashMap<String, String>();
            data.put("card_id", params[0]);

            String result = ruc.sendPostRequest(CONFIG.GET_ADS, data);
            try {
                jsonObject = new JSONObject(result);
                JSONArray contacts = jsonObject.getJSONArray("result");
                JSON_PARSE_DATA_AFTER_WEBCALL(contacts);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return result;
        }
    }
        getAds g = new getAds();
        g.execute("100","");
    }





    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Adsdata GetDataAdapter2 = new Adsdata();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                System.out.println("sarvesh"+Integer.parseInt(json.getString("Ad_id")));
                GetDataAdapter2.setId(Integer.parseInt(json.getString("Ad_id")));

                GetDataAdapter2.setTitle(json.getString("Ad_title"));

                GetDataAdapter2.setDesc(json.getString("Ad_description"));

                GetDataAdapter2.setUrl(json.getString("Ad_image"));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        final AdsRecylerviewAdapter cardsRecylerviewAdapter = new AdsRecylerviewAdapter(GetDataAdapter1, getActivity());


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cardsRecylerviewAdapter);
            }
        });

    }
}
