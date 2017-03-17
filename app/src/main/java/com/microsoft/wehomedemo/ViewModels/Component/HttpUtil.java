package com.microsoft.wehomedemo.ViewModels.Component;

import android.content.Entity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.microsoft.wehomedemo.ViewModels.Event.WhenGetInformations;
import com.microsoft.wehomedemo.ViewModels.Event.WhenGetName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by v-chaojz on 3/17/2017.
 */

public class HttpUtil {
    private WhenGetInformations whenGetInformations;
    public HttpUtil(WhenGetInformations wgi){
        this.whenGetInformations = wgi;
    }

    public void getCitiesInformation(String city){
        doGetInformation doGetInformation = new doGetInformation();
        doGetInformation.execute(city);
    }

    private void parseJSON(InputStream is){
        String contentString = null;
        ArrayList<String> details = new ArrayList<>();
        try {
            contentString = convertInputStreamToString(is, 5000);
            JSONObject object = new JSONObject(contentString);
            JSONObject data = object.getJSONObject("data");
            details.add("AQI:" + data.getInt("aqi"));
//            for (int i=0; i < data.length(); i++){
//                JSONObject jo = data.getJSONObject(i);
//                String id = jo.getString("id");
//                Boolean status = jo.getBoolean("status");
//                planList.AddPlan(id,status);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        whenGetInformations.invoke(this,details);
        Log.i("informations", contentString);
    }

    private class doGetInformation extends AsyncTask<String,Integer,InputStream>{

        @Override
        protected InputStream doInBackground(String... params) {
            InputStream is = null;
            try{
                URL url = new URL("https://api.waqi.info/feed/"+ params[0]+ "/?token=7dfc3bc68fcc0b7a19162e4f1d6ba51ff747df04");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(50000 /* milliseconds */);
                conn.setConnectTimeout(50000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();
                return is;
            } catch (Exception e){
                Log.e("Error"," " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            parseJSON(inputStream);
        }


    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
}
