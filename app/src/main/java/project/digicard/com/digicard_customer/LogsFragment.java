package project.digicard.com.digicard_customer;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import project.digicard.com.digicard_customer.Model.Adsdata;
import project.digicard.com.digicard_customer.Model.Carddata;
import project.digicard.com.digicard_customer.Model.Logsdata;

/**
 * Created by sarveshpalav on 13/02/17.
 */
public class LogsFragment extends Fragment {

    JSONObject jsonObject;
    List<Logsdata> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    SharedPreferences sharedPreferences;
    ProgressBar loading;
    TextView Fetch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GetDataAdapter1 = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_logs, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.logsrecylerview);
        loading = (ProgressBar) rootView.findViewById(R.id.progressBarLogs);
        Fetch = (TextView) rootView.findViewById(R.id.fetchinglogs);
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
                data.put("cust_id", params[0]);

                String result = ruc.sendPostRequest(CONFIG.GET_LOGS, data);
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
        g.execute("1","");
    }





    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Logsdata GetDataAdapter2 = new Logsdata();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                System.out.println("sarvesh"+Integer.parseInt(json.getString("L_amount")));
                GetDataAdapter2.setId(Integer.parseInt("1"));

              //  GetDataAdapter2.setTitle(json.getString("Ad_"));

                GetDataAdapter2.setBalance(json.getString("L_amount"));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        final LogsRecylerviewAdapter cardsRecylerviewAdapter = new LogsRecylerviewAdapter(GetDataAdapter1, getActivity());


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cardsRecylerviewAdapter);
            }
        });

    }
}


