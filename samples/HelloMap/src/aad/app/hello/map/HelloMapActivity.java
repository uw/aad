
package aad.app.hello.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class HelloMapActivity extends MapActivity implements OnClickListener, OnMapClickListener {

    private static final String TAG = HelloMapActivity.class.getSimpleName();

    private LocationManager mLocationManager;
    private MapFragment mMapFragment;
    
    private ArrayList<Marker> mNukeMarkers = new ArrayList<Marker>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mMapFragment = (MapFragment) this.getFragmentManager().findFragmentById(R.id.mapFragment);
        mMapFragment.getMap().setOnMapClickListener(this);
        mMapFragment.getMap().getUiSettings().setZoomControlsEnabled(false);        
        
        this.findViewById(R.id.findMeButton).setOnClickListener(this);
        this.findViewById(R.id.nukeButton).setOnClickListener(this);
    }
    
    protected void findMe() {

        // Get the location of the device
        Location l = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                
        // Get latitude and longitude of the current location
        double latitude = l.getLatitude();
        double longitude = l.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);      

        // Move to the current location in the Map        
        mMapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in and add the down arrow marker
        mMapFragment.getMap().animateCamera(CameraUpdateFactory.zoomTo(16));
        mMapFragment.getMap().addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Found you here!").icon(BitmapDescriptorFactory.fromResource(R.drawable.down_arrow)));
        
    }
    
    protected void nuke() {

        Log.d(TAG, "nuke()");
        
        for (Marker nukeMarker : mNukeMarkers) {            
            mMapFragment.getMap().addMarker(new MarkerOptions().position(nukeMarker.getPosition()).icon(BitmapDescriptorFactory.fromResource(R.drawable.nuke_explosion)));
            nukeMarker.remove();
        }        
        
        MediaPlayer mp = MediaPlayer.create(this, R.raw.nuke);
        mp.start();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onClick(View view) {
       
        switch (view.getId()) {
        
            case R.id.findMeButton:
                findMe();
                break;

            case R.id.nukeButton:
                nuke();
                break;
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        Marker nukeMarker = mMapFragment.getMap().addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.nuke)));
        mNukeMarkers.add(nukeMarker);
    };

}
