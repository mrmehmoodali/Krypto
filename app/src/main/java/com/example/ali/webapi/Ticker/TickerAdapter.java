package com.example.ali.webapi.Ticker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ali.webapi.R;

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
        public TextView max24;
        public TextView min24;
        public ImageView imageView;
        public View layout;
        CardView cardViewTicker;

        public ViewHolder(View v) {
            super(v);
            layout      = v;
            cryptoID    = v.findViewById(R.id.cryptoId);
            price       = v.findViewById(R.id.price);
            max24       = v.findViewById(R.id.max24);
            min24       = v.findViewById(R.id.min24);
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
        holder.max24.setText(map.get(TickerTab.KEY_PERCENT_CHANGE_1H));
        holder.min24.setText(map.get(TickerTab.KEY_PERCENT_CHANGE_24H));
        //holder.imageView.setImageResource(Integer.parseInt(map.get("icon")));
        //holder.imageView.setImageResource(R.drawable.);
        //Log.e(TAG, "Percent " + String.valueOf(map.get(TickerTab.KEY_PERCENT_CHANGE_1H)));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
