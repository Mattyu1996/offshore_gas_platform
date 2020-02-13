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
        TextView stato = view.findViewById(R.id.valore_stato);
        TextView minerale = view.findViewById(R.id.valore_minerale);
        TextView operatore = view.findViewById(R.id.valore_operatore);
        TextView titoloMinerario = view.findViewById(R.id.valore_titoloMinerario);
        TextView centraleCollegata = view.findViewById(R.id.valore_centraleCollegata);
        TextView zona = view.findViewById(R.id.valore_zona);
        TextView foglio = view.findViewById(R.id.valore_foglio);
        TextView sezioneUnimig = view.findViewById(R.id.valore_sezioneUnimig);
        TextView capitaneriaDiPorto = view.findViewById(R.id.valore_capitaneriaDiPorto);
        TextView dimensioni = view.findViewById(R.id.valore_dimensioni);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);
        TextView tipo = view.findViewById(R.id.valore_tipo);


        provider = ViewModelProviders.of(getActivity()).get(GasPlatformViewModel.class);
        provider.getCurrentPlatform().observe(this, new Observer<GasPlatform>() {
            @Override
            public void onChanged(GasPlatform platform) {

                denominazione.setText(platform.getDenominazione());

            }
        });


    }
}