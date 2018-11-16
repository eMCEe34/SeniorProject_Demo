package com.example.medwa.seniorproject_demo;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.ClusterManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    public Button mReportStatus;
    private ClusterManager<StatusMarkers> mClusterManager;
    private MyClusterManager mClusterManagerRenderer;
    private GoogleMap mGoogleMap;


    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mReportStatus = (Button)findViewById(R.id.reportStatusButton);

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        mReportStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStatusMarkers();

            }
        });
    }


    private void addStatusMarkers(){

        RouteInformation routeInfo = new RouteInformation();

        if(mGoogleMap != null){

            if(mClusterManager == null){
                mClusterManager = new ClusterManager<StatusMarkers>(this.getApplicationContext(), mGoogleMap);
            }
            if(mClusterManagerRenderer == null){
                mClusterManagerRenderer = new MyClusterManager(this, mGoogleMap, mClusterManager);
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }


                try{
                    String snippet = "";
                    snippet = "Running Late";

                    int avatar = R.drawable.delayed_bus; // set the default avatar

                    StatusMarkers newStatusMarker = new StatusMarkers(new LatLng(routeInfo.getLatitude(), routeInfo.getLongitude()), "N70 Bus", snippet, avatar );
                    mClusterManager.addItem(newStatusMarker);

                }catch (NullPointerException e){
                    Log.e("AddMapMarkers: ", "addMapMarkers: NullPointerException: " + e.getMessage() );
                }


            mClusterManager.cluster();

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        RouteInformation routeInfo = new RouteInformation();
        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        double bottom = routeInfo.getLatitude()-.1;
        double left = routeInfo.getLongitude()-.1;
        double top = routeInfo.getLatitude()+.1;
        double right = routeInfo.getLongitude()+.1;

        LatLngBounds boundary = new LatLngBounds(new LatLng(bottom,left), new LatLng(top,right));

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(boundary.getCenter(),15));

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
