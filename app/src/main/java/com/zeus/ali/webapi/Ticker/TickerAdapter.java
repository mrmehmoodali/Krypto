package com.zeus.ali.webapi.Ticker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zeus.ali.webapi.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ali on 1/11/2018.
 */

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String,String>> values;

    /*public TickerAdapter(Context context) {
        this.context = context;
    }*/


    // Provide a suitable constructor (depends on the kind of dataset)
    public TickerAdapter(ArrayList<HashMap<String,String>> myDataset) {
        values = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cryptoID;
        public TextView price;
        public TextView percent1H;
        public TextView percent24H;
        public ImageView imageView;
        public View layout;
        CardView cardViewTicker;

        public ViewHolder(View v) {
            super(v);
            layout      = v;
            cryptoID    = v.findViewById(R.id.cryptoId);
            price       = v.findViewById(R.id.price);
            percent1H = v.findViewById(R.id.max24);
            percent24H = v.findViewById(R.id.min24);
            imageView   = v.findViewById(R.id.imageView);
            cardViewTicker = v.findViewById(R.id.cardViewTicker);
        }
    }


    @Override
    public TickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view =
                inflater.inflate(R.layout.ticker_list_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TickerAdapter.ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.cardViewTicker);
        HashMap<String,String> map = values.get(position);
        holder.cryptoID.setText(map.get(TickerTab.KEY_SYMBOL));

        holder.price.setText(map.get(TickerTab.KEY_PRICE_INR));
        holder.percent1H.setText(map.get(TickerTab.KEY_PERCENT_CHANGE_1H));

        if (map.get(TickerTab.KEY_PERCENT_CHANGE_1H).substring(0,1).equals("-")){
            holder.percent1H.setTextColor(Color.RED);
        }
        else{
            holder.percent1H.setTextColor(Color.GREEN);
        }

        holder.percent24H.setText(map.get(TickerTab.KEY_PERCENT_CHANGE_24H));

        if (map.get(TickerTab.KEY_PERCENT_CHANGE_24H).substring(0,1).equals("-")){
            holder.percent24H.setTextColor(Color.RED);
        }
        else{
            holder.percent24H.setTextColor(Color.GREEN);
        }
        //holder.imageView.setImageResource(Integer.parseInt(map.get("icon")));
        //holder.imageView.setImageResource(R.drawable.);
        //Log.e(TAG, "Percent " + String.valueOf(map.get(TickerTab.KEY_PERCENT_CHANGE_1H)));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
