package project.digicard.com.digicard_customer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import project.digicard.com.digicard_customer.Adapters.CardsRecylerviewAdapter;
import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Model.Carddata;
import project.digicard.com.digicard_customer.Network.RequestHandler;
import project.digicard.com.digicard_customer.R;

import static project.digicard.com.digicard_customer.Model.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustid;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public class CardsFragment extends Fragment {

JSONObject jsonObject;
    List<Carddata> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    SharedPreferences sharedPreferences;
    ProgressBar loading;
    TextView Fetch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GetDataAdapter1 = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        View rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.cardrecylerview);
        loading = (ProgressBar)rootView.findViewById(R.id.progressBarCard);
        Fetch =(TextView) rootView.findViewById(R.id.fetchingcards);
        loading.setVisibility(View.INVISIBLE);
        Fetch.setVisibility(View.INVISIBLE);
                recyclerViewlayoutManager = new LinearLayoutManager(getActivity());


        recyclerView.setLayoutManager(recyclerViewlayoutManager);


                getCards();

        return rootView;
    }


    public void  getCards()
    {
        class  getCards extends AsyncTask<String,String,String>

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
System.out.println("abc"+s);
                loading.setVisibility(View.INVISIBLE);
                Fetch.setVisibility(View.INVISIBLE);

            }

            @Override
            protected String doInBackground(String... params) {

                RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String,String>();
              data.put("B_id",params[0]);

               String result =  ruc.sendPostRequest(CONFIG.GET_CARDS,data);
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
        g.execute(sharedPreferences.getString(sharedprefcustid,""));
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Carddata GetDataAdapter2 = new Carddata();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
System.out.println("milind"+Integer.parseInt(json.getString("Card_id")));
                GetDataAdapter2.setId(Integer.parseInt(json.getString("Card_id")));

                GetDataAdapter2.setName(json.getString("Card_name"));

                GetDataAdapter2.setBalance(Integer.parseInt(json.getString("Balance")));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

      final CardsRecylerviewAdapter cardsRecylerviewAdapter = new CardsRecylerviewAdapter(GetDataAdapter1, getActivity());


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cardsRecylerviewAdapter);
            }
        });

    }
}


