package project.digicard.com.digicard_customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static project.digicard.com.digicard_customer.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.CONFIG.sharedprefcustemail;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public  class URL_REQUEST {

 private    SharedPreferences sharedPreferences;
    public JSONObject jsonObject;
    SharedPreferences sharedpreferences;




    public   JSONObject getuserdetailsfromemail(String email,Context context)
   {
       sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

       class getuserdeatilsfromemail extends AsyncTask<String,String,String>

       {





           @Override
           protected String doInBackground(String... params) {

               HashMap<String, String> data = new HashMap<String,String>();
               data.put("email",params[0]);


RequestHandler ruc = new RequestHandler();
               String result = ruc.sendPostRequest(CONFIG.GET_USERINFO,data);
               System.out.println("hello"+result);
               try {
                   jsonObject =new JSONObject(result);
                   JSONArray contacts = jsonObject.getJSONArray("result");

                   for (int i = 0; i < contacts.length(); i++) {

                       JSONObject c = contacts.getJSONObject(i);

                       String id = c.getString("B_id");
                       String name = c.getString("B_name");
                       String email = c.getString("B_email");
                       String phone = c.getString("B_phone");
                       System.out.println("json"+id);
                       SharedPreferences.Editor editor = sharedpreferences.edit();
                       editor.putString(CONFIG.sharedprefcustid,id);
                       editor.putString(CONFIG.sharedprefcustphone,phone);
                       editor.putString(CONFIG.sharedprefcustname,name);
                       editor.putString(CONFIG.sharedprefcustemail,email);
                       editor.commit();
                   }


               } catch (JSONException e) {
                   e.printStackTrace();
               }
               return result;
           }

           @Override
           protected void onPostExecute(String s) {

               super.onPostExecute(s);



           }
       }


       getuserdeatilsfromemail getuserdeatilsfromemail = new getuserdeatilsfromemail();
       getuserdeatilsfromemail.execute(email);
       return jsonObject;

   }





}
