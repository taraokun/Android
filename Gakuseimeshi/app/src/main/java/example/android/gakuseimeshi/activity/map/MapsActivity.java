package example.android.gakuseimeshi.activity.map;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import example.android.gakuseimeshi.R;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private double destination_latitude;
    private double destination_longitude;

    private LatLng destination;

    private String destination_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);


        setLocation();
        setDestinationName();

        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    //Toast.makeText(getApplicationContext(), "タップ位置\n緯度：" + point.latitude + "\n経度:" + point.longitude, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MapsActivity2.class);
                    startActivity(intent);
                }
            });
        }

        // Add a marker in Sydney and move the camera
        //LatLng KIT = new LatLng(36.5310338 , 136.6284361);
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(destination_latitude, destination_longitude))
                .title(destination_name));
        CameraUpdate cUpdata = CameraUpdateFactory.newLatLngZoom(destination, 16);
        mMap.moveCamera(cUpdata);
    }

    //現在位置及び目的地をセット
    private void setLocation(){
        destination_latitude = 36.5310338;
        destination_longitude = 136.6284361;

        destination = new LatLng(destination_latitude, destination_longitude);
    }

    //目的地の名前をセット
    private void setDestinationName(){
        destination_name = "目的地";
    }
}
