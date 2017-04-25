package project.digicard.com.digicard_customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.digicard.com.digicard_customer.Adapters.CardsRecylerviewAdapter;
import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Model.Carddata;
import project.digicard.com.digicard_customer.Adapters.RecylerItemClick;
import project.digicard.com.digicard_customer.Network.RequestHandler;

import static project.digicard.com.digicard_customer.Model.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustid;

/**
 * Created by sarveshpalav on 15/03/17.
 */
public class DialogList extends AppCompatActivity {

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView recyclerView;
    JSONObject jsonObject;
    List<Carddata> GetDataAdapter1;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialoglist);
        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.cardrecylerview);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        recyclerView.addOnItemTouchListener(new RecylerItemClick(this,
                recyclerView, new RecylerItemClick.ClickListener() {


            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                TextView id,name ;
                id =(TextView)view.findViewById(R.id.id);
                name =(TextView)view.findViewById(R.id.name);

                Toast.makeText(DialogList.this,id.getText().toString().trim(),Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new CardEvent(id.getText().toString(),name.getText().toString()));
                finish();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        getCards();
    }
        public void  getCards()
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

        final CardsRecylerviewAdapter cardsRecylerviewAdapter = new CardsRecylerviewAdapter(GetDataAdapter1, this);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cardsRecylerviewAdapter);
            }
        });

    }



}
