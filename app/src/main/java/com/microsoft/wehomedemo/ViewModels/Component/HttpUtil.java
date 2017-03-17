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
import java.util.HashMap;

/**
 * Created by v-chaojz on 3/17/2017.
 */

public class HttpUtil {
    private WhenGetInformations whenGetInformations;
    public HttpUtil(WhenGetInformations wgi){
        this.whenGetInformations = wgi;
    }


    private class doGetNameFromGoogle extends AsyncTask<LatLng,Integer,InputStream>{

        @Override
        protected InputStream doInBackground(LatLng... params) {
            InputStream is = null;
            try{
                URL url = new URL("http://maps.google.com/maps/api/geocode/json?latlng=" + params[0].latitude+","+params[0].longitude+"%20&language=en_us&sensor=true");
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
