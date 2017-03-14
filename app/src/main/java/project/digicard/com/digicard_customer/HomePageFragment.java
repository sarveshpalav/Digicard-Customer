package project.digicard.com.digicard_customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static project.digicard.com.digicard_customer.CONFIG.MyPREFERENCES;

/**
 * Created by Sarvesh on 22-12-2016.
 */

public class HomePageFragment extends Fragment {
Button pay;
    TextView Logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.homepage_fragment, container, false);



pay = (Button)rootView.findViewById(R.id.pay);
        Logo =(TextView)rootView.findViewById(R.id.logo);
        Typeface typeface= Typeface.createFromAsset(getActivity().getAssets(), "cursive.ttf");
        Logo.setTypeface(typeface);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),QrScanner.class));
            }
        });



        return rootView;
    }
}
