package project.digicard.com.digicard_customer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.digicard.com.digicard_customer.Model.Adsdata;
import project.digicard.com.digicard_customer.Model.Carddata;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

        Adsdata getDataAdapter1 = getCardDataAdapter.get(position);

        holder.TitleTextView.setText(String.valueOf(getDataAdapter1.getTitle()));

        holder.IdTextView.setText(String.valueOf(getDataAdapter1.getId()));

        holder.DescTextview.setText(String.valueOf(getDataAdapter1.getDesc()));

        Picasso.with(context).load(getDataAdapter1.getUrl()).resize(120, 60).into(holder.AdimageView);
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
