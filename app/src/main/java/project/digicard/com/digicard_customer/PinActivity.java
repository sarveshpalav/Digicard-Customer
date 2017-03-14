package project.digicard.com.digicard_customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by sarveshpalav on 25/12/16.
 */
public class PinActivity extends AppCompatActivity {


    TextView Bill;
    TextView Cardname;
    String bill,Sid,cardname,otp,b_id;
    EditText pin;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        Bill =(TextView)findViewById(R.id.bill);
        Cardname =(TextView)findViewById(R.id.cardname);
        pin = (EditText)findViewById(R.id.pin);
        sharedPreferences = getSharedPreferences(CONFIG.MyPREFERENCES, Context.MODE_PRIVATE);

        Bundle b = getIntent().getExtras();
      Sid =  b.getString("Sid");
      cardname =  b.getString("cardname");
      bill =   b.getString("bill");
        otp = b.getString("otp");


      b_id =  sharedPreferences.getString(CONFIG.sharedprefcustid,null).trim();
        Toast.makeText(PinActivity.this,b_id,Toast.LENGTH_LONG).show();




        Bill.setText(bill);
        Cardname.setText(cardname);

        Button pay = (Button)findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_balance(pin.getText().toString().trim(),bill.trim(),otp.trim(),b_id.trim());
            }
        });



    }


    public void update_balance(String pin,String bill,String otp,String B_id)
    {
        class updatebalance extends AsyncTask<String,String,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s.trim().equals("1"))
                {
                    startActivity(new Intent(PinActivity.this,TransactionSucessful.class));
                    finish();
                }
                Toast.makeText(PinActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPreExecute() {
                loading = ProgressDialog.show(PinActivity.this, "Loading", null, true, true);
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("B_PIN", params[0].trim());
                data.put("Bill",params[1]);
                data.put("OTP",params[2]);
                data.put("B_id",params[3]);
                data.put("Card_id", "100".trim());

                RequestHandler requestHandler = new RequestHandler();
                String res  = requestHandler.sendPostRequest(CONFIG.PUT_PIN,data);

                return res;
            }
        }
        updatebalance updatebalance = new updatebalance();
        updatebalance.execute(pin,bill,otp,B_id);
    }

}
