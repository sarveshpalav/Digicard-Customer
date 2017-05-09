package project.digicard.com.digicard_customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.digicard.com.digicard_customer.Adapters.CardsRecylerviewAdapter;
import project.digicard.com.digicard_customer.Adapters.SearchCardRecylerviewAdapter;
import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Model.Carddata;
import project.digicard.com.digicard_customer.Network.RequestHandler;

import static project.digicard.com.digicard_customer.Model.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustid;

/**
 * Created by sarveshpalav on 03/05/17.
 */
public class CardSearchActivity extends AppCompatActivity {
    JSONObject jsonObject;
    List<Carddata> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    SharedPreferences sharedPreferences;
    ProgressBar loading;
    TextView Fetch;
    SearchView cardsearchview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cards);

        GetDataAdapter1 = new ArrayList<>();
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        recyclerView = (RecyclerView)findViewById(R.id.cardrecylerview);
        loading = (ProgressBar)findViewById(R.id.progressBarCard);
        Fetch =(TextView)findViewById(R.id.fetchingcards);
        cardsearchview =(SearchView)findViewById(R.id.searchviewcards);
        recyclerView = (RecyclerView)findViewById(R.id.cardrecylerview);
        loading = (ProgressBar)findViewById(R.id.progressBarCard);
        Fetch =(TextView)findViewById(R.id.fetchingcards);
        loading.setVisibility(View.INVISIBLE);
        Fetch.setVisibility(View.INVISIBLE);
        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);


        getCards();


cardsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return true;
    }
});


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
                data.put("card_id1","100");
                data.put("card_id2","105");


                String result =  ruc.sendPostRequest(CONFIG.SEARCH_CARD,data);
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

        final SearchCardRecylerviewAdapter cardsRecylerviewAdapter = new SearchCardRecylerviewAdapter(GetDataAdapter1,this);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cardsRecylerviewAdapter);
            }
        });

    }
}
