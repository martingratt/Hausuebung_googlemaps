package fh.ac.at.googlemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {


    protected LocationManager lm;
    protected GoogleMap map;
    int counter = 1;

    public static LinkedList<MarkerOptions> temporaryList = new LinkedList<>();
    public static LinkedList<MarkerOptions> endlist = new LinkedList<>();

    LatLng center = new LatLng(0, 0);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newSession = (Button)findViewById(R.id.bntNewSession);
        Button savedPosis = (Button)findViewById(R.id.savedMarkers);

        newSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                map.clear();
                map.moveCamera(CameraUpdateFactory.newLatLng(center));

            }
        });
        savedPosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });



        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        addLocationListener();


        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("LOCATION", location.getLatitude() + ":" + location.getLongitude());

        if (map != null){

            if (temporaryList.size()<5) {


                LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());

                MarkerOptions mo = new MarkerOptions();
                mo.title("Meine Position");
                mo.position(myPosition);




                //map.clear();
                map.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
                temporaryList.add(mo);
                endlist.add(mo);
                map.clear();

                for (int i=0; i < temporaryList.size(); i++){
                    map.addMarker(temporaryList.get(i));
                }




            } else {
                Toast.makeText(getApplicationContext(), "über 5",Toast.LENGTH_SHORT).show();
                temporaryList.removeFirst();
                LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions mo = new MarkerOptions();
                mo.title("Meine Position");
                mo.position(myPosition);
                temporaryList.add(mo);
                map.moveCamera(CameraUpdateFactory.newLatLng(myPosition));

                map.clear();
                for (int i=0; i < temporaryList.size(); i++){
                    map.addMarker(temporaryList.get(i));
                }
            }



        }



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

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[]grantResult){
        if (requestCode == 666 && grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
            addLocationListener();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }




}
