package project.digicard.com.digicard_customer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.digicard.com.digicard_customer.Model.Logsdata;
import project.digicard.com.digicard_customer.R;

/**
 * Created by sarveshpalav on 13/02/17.
 */
public class LogsRecylerviewAdapter extends RecyclerView.Adapter<LogsRecylerviewAdapter.ViewHolder> {


    Context context;

    List<Logsdata> getCardDataAdapter;

    public LogsRecylerviewAdapter(List<Logsdata> getDataAdapter, Context context) {

        super();

        this.getCardDataAdapter = getDataAdapter;
        this.context = context;
    }


    @Override
    public LogsRecylerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logrecylerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LogsRecylerviewAdapter.ViewHolder holder, int position) {
        Logsdata getDataAdapter1 = getCardDataAdapter.get(position);

       // holder.TitleTextView.setText(String.valueOf(getDataAdapter1.()));

        holder.IdTextView.setText(String.valueOf(getDataAdapter1.getId()));

        System.out.println("yoyo"+getDataAdapter1.getBalance());

        holder.DescTextview.setText(getDataAdapter1.getBalance());
    }

    @Override
    public int getItemCount() {
        return getCardDataAdapter.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView IdTextView;
        public TextView TitleTextView;
        public TextView DescTextview;


        public ViewHolder(View itemView) {

            super(itemView);


            IdTextView = (TextView) itemView.findViewById(R.id.id);
            TitleTextView = (TextView) itemView.findViewById(R.id.receiver);
            DescTextview = (TextView) itemView.findViewById(R.id.amount);


        }
    }
}
