package com.microsoft.wehomedemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.wehomedemo.ViewModels.Event.WhenGetName;
import com.microsoft.wehomedemo.ViewModels.ListViewModel;
import com.microsoft.wehomedemo.ViewModels.MapViewModel;

public class main extends FragmentActivity {
    private MapViewModel myMapView;
    private ListViewModel myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        myMapView = new MapViewModel(this,whenGetName);
        myListView = new ListViewModel(this,(ListView)findViewById(R.id.listView));
        mapFragment.getMapAsync(myMapView);
    }

    public WhenGetName whenGetName = new WhenGetName() {
        @Override
        public void invoke(Object sender, String name) {
            myListView.getDetailInformation(name);
        }
    };

}
