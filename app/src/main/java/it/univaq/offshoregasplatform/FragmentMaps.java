package it.univaq.offshoregasplatform;

import com.google.android.gms.maps.OnMapReadyCallback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class FragmentMaps extends Fragment implements OnMapReadyCallback {

    private GasPlatformViewModel provider;

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
    public void onMapReady(final GoogleMap googleMap) {

        if(getArguments() != null) {
            GasPlatform platform = getArguments().getParcelable("platforms");
            if (platform != null) {

                MarkerOptions options = new MarkerOptions();
                options.title(platform.getDenominazione());
                options.position(new LatLng(platform.getLatitudine(), platform.getLongitudine()));

                googleMap.addMarker(options);

                googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                new LatLng(platform.getLatitudine(), platform.getLongitudine()), 12f));
            }
        }
        else  {
            provider.getPlatforms().observe(this, new Observer<ArrayList<GasPlatform>>() {
                @Override
                public void onChanged(ArrayList<GasPlatform> platforms) {

                    while(platforms.iterator().hasNext()){
                        GasPlatform current =platforms.iterator().next();

                        MarkerOptions options = new MarkerOptions();
                        options.title(current.getDenominazione());
                        options.position(new LatLng(current.getLatitudine(), current.getLongitudine()));

                        googleMap.addMarker(options);
                    }
                }
            });
        }
    }

}
