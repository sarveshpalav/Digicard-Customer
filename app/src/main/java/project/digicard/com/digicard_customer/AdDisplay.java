package project.digicard.com.digicard_customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import project.digicard.com.digicard_customer.R;
import project.digicard.com.digicard_customer.Services.MyFirebaseMessagingService;

/**
 * Created by Sarvesh on 5/2/2017.
 */

public class AdDisplay extends AppCompatActivity {

ImageView ImageAdImage;
    TextView TextViewAdTitle,TextViewAdDesc;
    String AdDesc,AdTitle,AdImageUrl;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addisplay);
        Intent i = new Intent();
       AdDesc = i.getStringExtra("AdImage");
      AdTitle =    i.getStringExtra("AdTitle");
      AdImageUrl =  i.getStringExtra("AdImageUrl");



        ImageAdImage = (ImageView) findViewById(R.id.adimage);
        TextViewAdTitle =(TextView)findViewById(R.id.adtitle);
        TextViewAdDesc =(TextView)findViewById(R.id.addesc);


        Picasso.with(this).load(MyFirebaseMessagingService.AdImageurl).into(ImageAdImage);

        TextViewAdTitle.setText(MyFirebaseMessagingService.AdTitle);
        TextViewAdDesc.setText(MyFirebaseMessagingService.AdDesc);



    }
}
