package project.digicard.com.digicard_customer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.digicard.com.digicard_customer.R;

import static project.digicard.com.digicard_customer.Model.CONFIG.MyPREFERENCES;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustemail;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustid;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustname;
import static project.digicard.com.digicard_customer.Model.CONFIG.sharedprefcustphone;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public class ProfileFragment extends Fragment {
TextView email,id,phoneno,name;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        email =(TextView)rootView.findViewById(R.id.email);
        id =(TextView)rootView.findViewById(R.id.id);
        phoneno =(TextView)rootView.findViewById(R.id.phoneno);
        name =(TextView)rootView.findViewById(R.id.name);




        email.setText(sharedPreferences.getString(sharedprefcustemail,""));
//        id.setText(sharedPreferences.getString(sharedprefcustid,""));
        phoneno.setText(sharedPreferences.getString(sharedprefcustphone,""));
        name.setText(sharedPreferences.getString(sharedprefcustname,""));

        return rootView;


    }
}
