package quixada.ufc.br.kisan.activity;

import android.location.Address;
import android.location.Geocoder;
import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    CustomApplication customApplication;
    Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        customApplication = (CustomApplication) this.getApplicationContext();

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(usuario.getCidade(), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);

        double longitude = address.getLongitude();
        double latitude = address.getLatitude();

        LatLng cidade = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(cidade).title("Este local é " + usuario.getCidade()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cidade, 15));

    }

}
