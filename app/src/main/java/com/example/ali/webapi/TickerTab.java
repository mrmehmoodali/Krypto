package com.example.ali.webapi;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Created by Ali on 1/4/2018.
 */

public class TickerTab extends Fragment {

    private static final String API_URL = "https://koinex.in/api/ticker";
    ArrayList<HashMap<String, String>> cryptoList;

    int[] icons = new int[]{
            R.drawable.ic_btc,
            R.drawable.ic_eth,
            R.drawable.ic_xrp,
            R.drawable.ic_bch,
            R.drawable.ic_ltc,
            R.drawable.ic_miota,
            R.drawable.ic_omg,
            R.drawable.ic_gnt,
    };
    ProgressBar mProgressBar;
    //TextView mResponseView;
    ListView mListView;
    //SwipeRefreshLayout swipeRefreshLayout;
    //TickerView mTickerView;
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mProgressBar = findViewById(R.id.progressBar);
        //mResponseView = getView().findViewById(R.id.responseView);
        //swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        //new RetrieveFeedTask().execute();

        //swipeRefreshLayout.setOnRefreshListener(this);

        /*
          Showing Swipe Refresh animation on activity create
          As animation won't start on onCreate, post runnable is used
         */
        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new RetrieveFeedTask().execute();
                                    }
                                }
        );

        new RetrieveFeedTask().execute();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.ticker_tab,container,false);
        //mResponseView = v.findViewById(R.id.responseView);
        mListView = v.findViewById(R.id.list);
        cryptoList = new ArrayList<>();
        mProgressBar = v.findViewById(R.id.progressBar);
        //TextView mResponseView = v.findViewById(R.id.responseView);
        //swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        new RetrieveFeedTask().execute();
        return v;

    }

    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mResponseView = getView().findViewById(R.id.responseView);
    }*/

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        //private Exception exception;

        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
            //mResponseView.setText(" ");

        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String response = sh.makeServiceCall(API_URL);

            Log.e(TAG, "Response from url: " + response);

            return response;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            mProgressBar.setVisibility(View.GONE);
            //Log.i("INFO", response);
            //mResponseView.setText(response);
            //swipeRefreshLayout.setRefreshing(false);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                /*JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONObject rate = object.getJSONObject("prices");
                String xTicker = rate.getString("XRP");
                //mTickerView.setText(xTicker);
                mResponseView.setText(xTicker);
                //int likelihood = object.getInt("likelihood");
                //JSONArray photos = object.getJSONArray("photos");*/

                JSONObject jsonObj = new JSONObject(response);
                JSONObject rate = jsonObj.getJSONObject("prices");
                JSONObject stats = jsonObj.getJSONObject("stats");
                Integer i = 0;
                for(Iterator<String> iter1 = rate.keys(); iter1.hasNext();) {
                    HashMap<String, String> temp = new HashMap<>();
                    String key1 = iter1.next();
                    Object price = rate.get(key1);
                    temp.put("id", key1);
                    temp.put("price", String.valueOf(price));
                    temp.put("icon", Integer.toString(icons[i]) );
                    i++;
                    //Log.i("iter", String.valueOf(i));
                    for(Iterator<String> iter2 = stats.keys(); iter2.hasNext();) {
                        String key2 = iter2.next();

                        if (Objects.equals(key1, key2)) {
                            //Log.i("KEY1+KEY2", key1+"+"+key2);
                            JSONObject coin = stats.getJSONObject(key2);
                            String highestBid = coin.getString("highest_bid");
                            String lastTradedPrice = coin.getString("last_traded_price");
                            String lowestAsk = coin.getString("lowest_ask");
                            String max24hrs = coin.getString("max_24hrs");
                            String min24hrs = coin.getString("min_24hrs");
                            String vol24hrs = coin.getString("vol_24hrs");
                            temp.put("hbid", highestBid);
                            temp.put("ltprice", lastTradedPrice);
                            temp.put("lask", lowestAsk);
                            temp.put("max24", max24hrs);
                            temp.put("min24", min24hrs);
                            temp.put("vol24", vol24hrs);
                        }
                    }
                    cryptoList.add(temp);

                }

                //Log.i("LIST", String.valueOf(Arrays.asList(cryptoList)));

                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), cryptoList,
                        R.layout.ticker_list_items, new String[]{"id","price","icon","max24","min24"},
                        new int[]{R.id.cryptoId, R.id.price, R.id.imageView, R.id.max24, R.id.min24});

                mListView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}