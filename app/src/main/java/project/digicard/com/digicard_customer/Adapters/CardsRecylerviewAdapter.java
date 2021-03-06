package project.digicard.com.digicard_customer.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Model.Carddata;
import project.digicard.com.digicard_customer.Model.EventsModel;
import project.digicard.com.digicard_customer.Network.RequestHandler;
import project.digicard.com.digicard_customer.R;

import static project.digicard.com.digicard_customer.Model.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustid;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public class CardsRecylerviewAdapter extends RecyclerView.Adapter<CardsRecylerviewAdapter.ViewHolder> {

    Context context;

    List<Carddata> getCardDataAdapter;
    SharedPreferences sharedPreferences;

    public CardsRecylerviewAdapter(List<Carddata> getDataAdapter, Context context) {

        super();

        this.getCardDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrecylerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Carddata getDataAdapter1 = getCardDataAdapter.get(position);

        holder.NameTextView.setText(String.valueOf(getDataAdapter1.getName()));

        holder.IdTextView.setText(String.valueOf(getDataAdapter1.getId()));

        holder.BalanceTextview.setText(String.valueOf(getDataAdapter1.getBalance()));


    }

    @Override
    public int getItemCount() {

        return getCardDataAdapter.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView IdTextView;
        public TextView NameTextView;
        public TextView BalanceTextview;
        public Button Unsubscribe;
        public RelativeLayout layout;




        public ViewHolder(View itemView) {

            super(itemView);


            IdTextView = (TextView) itemView.findViewById(R.id.id);
            NameTextView = (TextView) itemView.findViewById(R.id.name);
            BalanceTextview = (TextView) itemView.findViewById(R.id.balance);
            Unsubscribe = (Button) itemView.findViewById(R.id.unsubscribe);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);


            Unsubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    sharedPreferences.getString(sharedprefcustid, "");
                  //  EventBus.getDefault().post(new EventsModel("cardupdate"));
                     Unsubsribecall(IdTextView.getText().toString().trim(), sharedPreferences.getString(sharedprefcustid, ""),BalanceTextview.getText().toString().trim());

                }
            });
        }


        public void  Unsubsribecall(String card_id,String  B_id,String Balance   )
        {
            class  Unsubscribe extends AsyncTask<String,String,String>

            {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);


                    if(s.equals("1"))
                    {
                        EventBus.getDefault().post(new EventsModel("cardupdate"));
                    }else
                    {
                        Toast.makeText(context,"Your Balance Should be Zero",Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                protected String doInBackground(String... params) {

                    RequestHandler ruc = new RequestHandler();

                    HashMap<String, String> data = new HashMap<String,String>();
                    data.put("Card_id",params[0]);
                    data.put("B_id",params[1]);
                    data.put("Balance",params[2]);

                    String result =  ruc.sendPostRequest(CONFIG.UNSUBSCRIBE_CARD,data);



                    return result ;
                }
            }

            Unsubscribe g = new Unsubscribe();
            g.execute(card_id,B_id,Balance);
        }





        }

}
