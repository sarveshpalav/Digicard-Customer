package project.digicard.com.digicard_customer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.digicard.com.digicard_customer.AdDisplay;
import project.digicard.com.digicard_customer.Model.Adsdata;
import project.digicard.com.digicard_customer.R;
import project.digicard.com.digicard_customer.Services.MyFirebaseMessagingService;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public class AdsRecylerviewAdapter extends RecyclerView.Adapter<AdsRecylerviewAdapter.ViewHolder> {

    Context context;

    List<Adsdata> getCardDataAdapter;

    public AdsRecylerviewAdapter(List<Adsdata> getDataAdapter, Context context) {

        super();

        this.getCardDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adrecylerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Adsdata getDataAdapter1 = getCardDataAdapter.get(position);

        holder.TitleTextView.setText(String.valueOf(getDataAdapter1.getTitle()));

        holder.IdTextView.setText(String.valueOf(getDataAdapter1.getId()));

        holder.DescTextview.setText(String.valueOf(getDataAdapter1.getDesc()));

        Picasso.with(context).load(getDataAdapter1.getUrl()).resize(120, 60).into(holder.AdimageView);

        holder.AdimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFirebaseMessagingService.AdTitle = holder.TitleTextView.getText().toString();
                MyFirebaseMessagingService.AdDesc = holder.DescTextview.getText().toString();
                MyFirebaseMessagingService.AdImageurl = getDataAdapter1.getUrl();
                context.startActivity(new Intent(context, AdDisplay.class));

            }
        });
    }

    @Override
    public int getItemCount() {

        return getCardDataAdapter.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView IdTextView;
        public TextView TitleTextView;
        public TextView DescTextview;
        public ImageView AdimageView;


        public ViewHolder(View itemView) {

            super(itemView);


            IdTextView = (TextView) itemView.findViewById(R.id.id);
            TitleTextView = (TextView) itemView.findViewById(R.id.title);
            DescTextview = (TextView) itemView.findViewById(R.id.desc);
            AdimageView=(ImageView)itemView.findViewById(R.id.adimage);




        }
    }
}
