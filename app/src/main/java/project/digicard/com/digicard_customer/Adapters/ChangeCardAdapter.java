package project.digicard.com.digicard_customer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.digicard.com.digicard_customer.Model.Carddata;
import project.digicard.com.digicard_customer.R;

/**
 * Created by Sarvesh on 4/26/2017.
 */

public class ChangeCardAdapter extends RecyclerView.Adapter<ChangeCardAdapter.ViewHolder> {

    Context context;

    List<Carddata> getCardDataAdapter;

    public ChangeCardAdapter(List<Carddata> getDataAdapter, Context context) {

        super();

        this.getCardDataAdapter = getDataAdapter;
        this.context = context;
    }


    @Override
    public ChangeCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chnagecarditem, parent, false);

        ChangeCardAdapter.ViewHolder viewHolder = new ChangeCardAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChangeCardAdapter.ViewHolder holder, int position) {

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


        public ViewHolder(View itemView) {

            super(itemView);


            IdTextView = (TextView) itemView.findViewById(R.id.id);
            NameTextView = (TextView) itemView.findViewById(R.id.name);
            BalanceTextview = (TextView) itemView.findViewById(R.id.balance);


        }
    }
}
