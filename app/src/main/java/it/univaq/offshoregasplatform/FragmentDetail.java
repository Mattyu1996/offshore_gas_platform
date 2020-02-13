package it.univaq.offshoregasplatform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

public class FragmentDetail extends Fragment {

    private GasPlatformViewModel provider;

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
        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView denominazione = view.findViewById(R.id.valore_denominazione);
        provider = ViewModelProviders.of(getActivity()).get(GasPlatformViewModel.class);
        provider.getCurrentPlatform().observe(this, new Observer<GasPlatform>() {
            @Override
            public void onChanged(GasPlatform platform) {

                denominazione.setText(platform.getDenominazione());

            }
        });


    }
}