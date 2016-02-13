package quixada.ufc.br.kisan.activity;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.application.CustomApplication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    CustomApplication customApplication;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        customApplication = (CustomApplication) this.getApplicationContext();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {

            addresses = geocoder.getFromLocationName(customApplication.getUsuario().getCidade(), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);

        double longitude = address.getLongitude();
        double latitude = address.getLatitude();

        // Add a marker in Sydney and move the camera
        LatLng cidade = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(cidade).title("Marker in " + customApplication.getUsuario().getCidade()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cidade));


    }
}
