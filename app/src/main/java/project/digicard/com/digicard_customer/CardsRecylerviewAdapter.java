package project.digicard.com.digicard_customer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.digicard.com.digicard_customer.Model.Carddata;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public class CardsRecylerviewAdapter extends RecyclerView.Adapter<CardsRecylerviewAdapter.ViewHolder> {

    Context context;

    List<Carddata> getCardDataAdapter;

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


        public ViewHolder(View itemView) {

            super(itemView);


            IdTextView = (TextView) itemView.findViewById(R.id.id);
            NameTextView = (TextView) itemView.findViewById(R.id.name);
            BalanceTextview = (TextView) itemView.findViewById(R.id.balance);


        }
    }
}
