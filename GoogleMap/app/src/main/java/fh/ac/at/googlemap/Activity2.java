package fh.ac.at.googlemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

import static fh.ac.at.googlemap.MainActivity.endlist;

public class Activity2 extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    protected LocationManager lm;
    protected GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        addLocationListener();

        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);

        LinkedList<MarkerOptions> test = MainActivity.endlist;

        String sizeArraylist = String.valueOf(MainActivity.endlist.get(0));

        Toast.makeText(getApplicationContext(), sizeArraylist, Toast.LENGTH_SHORT).show();

        //Warum NullPointerException???
        //Polyline line = map.addPolyline(new PolylineOptions().add(endlist));



        for (int i=0; i < test.size(); i++){
            map.addMarker(test.get(i));
        }





        }



    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onMapReady(GoogleMap googleMap) {



    }

    public void addLocationListener(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            } else {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 8);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[]grantResult){
        if (requestCode == 666 && grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
            addLocationListener();
        }
    }

};


