package com.microsoft.wehomedemo.ViewModels;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.microsoft.wehomedemo.ViewModels.Component.HttpUtil;
import com.microsoft.wehomedemo.ViewModels.Component.myTileOverlay;
import com.microsoft.wehomedemo.ViewModels.Event.WhenGetInformations;
import com.microsoft.wehomedemo.ViewModels.Event.WhenGetName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-chaojz on 3/17/2017.
 */

public class MapViewModel implements OnMapReadyCallback {
    private GoogleMap mMap;
    private myTileOverlay myTile;
    private LocationManager mLocationManager;
    private Context main;
    private String locationProvider;
    private HttpUtil httpUtil;
    private WhenGetName whenGetName;

    public MapViewModel(Context context,WhenGetName wn) {
        myTile = new myTileOverlay();
        this.main = context;
        mLocationManager = (LocationManager) main.getSystemService(main.LOCATION_SERVICE);
        httpUtil = new HttpUtil(whenGetInformations);
        this.whenGetName = wn;
    }



    GoogleMap.OnMapLongClickListener getInformations = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            Log.i("Lat"," " + latLng.latitude);
            Log.i("Lon"," " + latLng.longitude);
            List<android.location.Address> addList = null;
            Geocoder ge = new Geocoder(main);
            try {
                addList = ge.getFromLocation(latLng.latitude,latLng.longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addList!=null && addList.size()>0){
                Address ad = addList.get(0);
                whenGetName.invoke(this,ad.getAdminArea());
            }
//            httpUtil.getCityName(latLng);
        }
    };

    private WhenGetInformations whenGetInformations = new WhenGetInformations() {
        @Override
        public void invoke(Object sender, ArrayList<String> information) {

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(myTile.getmyTile()));
        mMap.setOnMapLongClickListener(getInformations);
        if (ActivityCompat.checkSelfPermission(main, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(main, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        mLocationManager.requestSingleUpdate(criteria, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("LatLng","Changed");
                if (location != null){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        },null);
        Location myLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation != null){
            LatLng myL = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myL));
        }else {

        }
    }



}
