package com.example.ali.webapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ali on 1/4/2018.
 */

public class MainActivityTickerTab extends Fragment {

    private static final String API_URL = "https://koinex.in/api/ticker";

    //ProgressBar mProgressBar;
    TextView mResponseView;
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
        View v =inflater.inflate(R.layout.activity_ticker_tab,container,false);
        mResponseView = v.findViewById(R.id.responseView);
        //mProgressBar = findViewById(R.id.progressBar);
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

            //mProgressBar.setVisibility(View.VISIBLE);
            mResponseView.setText(" ");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            //mProgressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            //mResponseView.setText(response);
            //swipeRefreshLayout.setRefreshing(false);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONObject rate = object.getJSONObject("prices");
                String xTicker = rate.getString("XRP");
                //mTickerView.setText(xTicker);
                mResponseView.setText(xTicker);
                //int likelihood = object.getInt("likelihood");
                //JSONArray photos = object.getJSONArray("photos");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}