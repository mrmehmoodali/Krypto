package com.zeus.ali.webapi.Ticker;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeus.ali.webapi.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ali on 1/11/2018.
 */

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String,String>> values;
    private int mExpandedPosition = -1;
    private RecyclerView passedRView;
    private ConstraintLayout rootConst;

    /*public TickerAdapter(Context context) {
        this.context = context;
    }*/


    // Provide a suitable constructor (depends on the kind of dataset)
    public TickerAdapter(ArrayList<HashMap<String,String>> myDataset) {
        values = myDataset;
    }

    public TickerAdapter(ArrayList<HashMap<String,String>> myDataset, RecyclerView passedRView) {
        values = myDataset;
        this.passedRView = passedRView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cryptoID;
        public TextView price;
        public TextView fullName;
        public TextView percent1H;
        public TextView percent24H;
        public TextView percent7D;
        public ImageView imageView;
        public View layout;
        CardView cardViewTicker;
        public ConstraintLayout constraintLayout;
        public ViewGroup testView;

        public ViewHolder(View v) {
            super(v);
            layout      = v;
            cryptoID    = v.findViewById(R.id.cryptoId);
            price       = v.findViewById(R.id.price);
            fullName = v.findViewById(R.id.fullName);
            percent1H = v.findViewById(R.id.per1H);
            percent24H = v.findViewById(R.id.per24H);
            percent7D = v.findViewById(R.id.per7day);
            imageView   = v.findViewById(R.id.imageView);
            cardViewTicker = v.findViewById(R.id.cardViewTicker);
            constraintLayout = v.findViewById(R.id.constraintLayout);
            testView = v.findViewById(R.id.my_recycler_view);
            rootConst = v.findViewById(R.id.rootConst);
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
    public void onBindViewHolder(final TickerAdapter.ViewHolder holder, final int position) {
        //YoYo.with(Techniques.FadeIn).playOn(holder.cardViewTicker);
        HashMap<String,String> map = values.get(position);
        holder.cryptoID.setText(map.get(TickerTab.KEY_SYMBOL));
        holder.price.setText(map.get(TickerTab.KEY_PRICE_INR));
        holder.fullName.setText(map.get(TickerTab.KEY_NAME));


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

        holder.percent7D.setText(map.get(TickerTab.KEY_PERCENT_CHANGE_7D));

        if (map.get(TickerTab.KEY_PERCENT_CHANGE_7D).substring(0,1).equals("-")){
            holder.percent7D.setTextColor(Color.RED);
        }
        else{
            holder.percent7D.setTextColor(Color.GREEN);
        }
        //holder.imageView.setImageResource(Integer.parseInt(map.get("icon")));
        //holder.imageView.setImageResource(R.drawable.);

        //The portion below adds the card expand functionality
        final boolean isExpanded = position==mExpandedPosition;
        holder.constraintLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;

                /*ChangeBounds transition = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    transition = new ChangeBounds();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    transition.setDuration(125);
                }*/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(rootConst);
                }

                //This removes the blink animation
                ((SimpleItemAnimator) passedRView.getItemAnimator()).setSupportsChangeAnimations(false);

                //notifyDataSetChanged();
                //notifyItemChanged(position);

                notifyItemRangeChanged(0, values.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
