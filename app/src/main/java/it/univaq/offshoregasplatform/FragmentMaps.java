package it.univaq.offshoregasplatform;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class FragmentMaps extends Fragment implements OnMapReadyCallback {

    private GasPlatformViewModel provider;
    private MainActivity current;

    public static FragmentMaps getInstance(ArrayList<GasPlatform> platforms) {
        FragmentMaps f = new FragmentMaps();

        Bundle b = new Bundle();
        b.putParcelableArrayList("platforms", platforms);

        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        provider = ViewModelProviders.of(getActivity()).get(GasPlatformViewModel.class);

        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            current = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        current = null;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //Piazzo il marcatore dello smartphone
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //Imposto la posizione della mappa sulla posizione dello smartphone
                googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(), location.getLongitude()), 12f));

                //Piazzo il marcatore dello smartphone
                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(location.getLatitude(), location.getLongitude()));
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                googleMap.addMarker(options);

            }
        });

        if(getArguments() != null) {

            //Piazzo i marcatori delle piattaforme
            ArrayList<GasPlatform> platforms = getArguments().getParcelableArrayList("platforms");
            for(GasPlatform plt: platforms){
                Marker marker = googleMap.addMarker( new MarkerOptions()
                        .title(plt.getDenominazione())
                        .position(new LatLng(plt.getLatitudine(), plt.getLongitudine())));
                marker.setTag(plt);
            }

        }
        else  {
            ArrayList<GasPlatform> platforms = provider.getNearPlatforms().getValue();
            for(GasPlatform plt: platforms){
                Marker marker = googleMap.addMarker( new MarkerOptions()
                .title(plt.getDenominazione())
                .position(new LatLng(plt.getLatitudine(), plt.getLongitudine())));
                marker.setTag(plt);

            }

        }

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
            @Override
            public void onInfoWindowClick(Marker marker) {
                //ottengo la posizione del marker
                GasPlatform plt = (GasPlatform) marker.getTag();
                //Faccio partire l'activity per i dettagli
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.setAction("DETAILS");
                intent.putExtra("platform", plt);
                startActivity(intent);
            }
        });
    }

}
