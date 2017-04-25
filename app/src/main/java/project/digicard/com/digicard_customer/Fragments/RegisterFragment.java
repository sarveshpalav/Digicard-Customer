package project.digicard.com.digicard_customer.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import project.digicard.com.digicard_customer.Model.CONFIG;
import project.digicard.com.digicard_customer.Network.RequestHandler;
import project.digicard.com.digicard_customer.R;

/**
 * Created by Sarvesh on 21-12-2016.
 */

public class RegisterFragment extends Fragment {

EditText editemail,editpassword,editname,editphone;
    String email,password,name,phone;
    Button registerbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

editemail =(EditText)rootView.findViewById(R.id.email);
        editpassword =(EditText)rootView.findViewById(R.id.password);
        editname =(EditText)rootView.findViewById(R.id.name);
        editphone =(EditText)rootView.findViewById(R.id.phoneno);

        registerbtn =(Button)rootView.findViewById(R.id.registerbutton);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editemail.getText().toString().trim().equals(""))
                {
                    editemail.setError("Please Enter Email");
                }

                else  if(editpassword.getText().toString().trim().equals(""))
                {
                    editpassword.setError("Please Enter Password");
                }
              else  if(editphone.getText().toString().trim().equals(""))
                {
                    editphone.setError("Please Enter Phone");
                }
              else   if(editname.getText().toString().trim().equals(""))
                {
                    editname.setError("Please Enter Name");
                }

                else
                {
                    registerUser();
                }

            }
        });

        return rootView;
    }

    private void registerUser() {


        email = editemail.getText().toString().trim();
        password = editpassword.getText().toString().trim();
        name = editname.getText().toString().trim();
        phone = editphone.getText().toString().trim();

        register(name,password,email,phone);


    }


    private void register(String name, String password, String email,String phone) {

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            RequestHandler ruc = new RequestHandler();// Its a Seperate Class file. We make am object of it.

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait",null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("password",params[1]);
                data.put("email",params[2]);
                data.put("phone",params[3]);

                String result = ruc.sendPostRequest(CONFIG.REGISTER_URL,data);

                return  result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s, Toast.LENGTH_LONG).show();
            }

        }
        //You make an object of RegisterUser  which you defined above as an Async Task.
        //Then you execute it.
        //It goes through preexecute, doin Background and PostExecute in order.

        RegisterUser ru = new RegisterUser();
        ru.execute(name,password,email,phone);
    }


}
