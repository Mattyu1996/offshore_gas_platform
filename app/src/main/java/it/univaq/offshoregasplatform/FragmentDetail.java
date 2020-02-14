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
        final TextView stato = view.findViewById(R.id.valore_stato);
        final TextView tipo = view.findViewById(R.id.valore_tipo);
        final TextView minerale = view.findViewById(R.id.valore_minerale);
        final TextView operatore = view.findViewById(R.id.valore_operatore);
        final TextView titoloMinerario = view.findViewById(R.id.valore_titoloMinerario);
        final TextView centraleCollegata = view.findViewById(R.id.valore_centraleCollegata);
        final TextView zona = view.findViewById(R.id.valore_zona);
        final TextView foglio = view.findViewById(R.id.valore_foglio);
        final TextView sezioneUnimig = view.findViewById(R.id.valore_sezioneUnimig);
        final TextView capitaneriaDiPorto = view.findViewById(R.id.valore_capitaneriaDiPorto);
        final TextView dimensioni = view.findViewById(R.id.valore_dimensioni);
        final TextView codice = view.findViewById(R.id.valore_codice);
        final TextView annoCostruzione = view.findViewById(R.id.valore_annoCostruzione);
        final TextView pozziAllacciati = view.findViewById(R.id.valore_pozziAllacciati);
        final TextView pozziProduttiviNonEroganti = view.findViewById(R.id.valore_pozziProduttiviNonEroganti);
        final TextView pozziInProduzione = view.findViewById(R.id.valore_pozziInProduzione);
        final TextView pozziInMonitoraggio = view.findViewById(R.id.valore_pozziInMonitoraggio);
        final TextView distanzaCosta = view.findViewById(R.id.valore_distanzaCosta);
        final TextView altezza = view.findViewById(R.id.valore_altezza);
        final TextView profonditaFondale = view.findViewById(R.id.valore_profonditaFondale);


        provider = ViewModelProviders.of(getActivity()).get(GasPlatformViewModel.class);
        provider.getCurrentPlatform().observe(this, new Observer<GasPlatform>() {
            @Override
            public void onChanged(GasPlatform platform) {
                System.out.println(platform.getCodice());
                denominazione.setText(platform.getDenominazione());
                stato.setText(platform.getStato());
                tipo.setText(platform.getTipo());
                minerale.setText(platform.getMinerale());
                operatore.setText(platform.getOperatore());
                titoloMinerario.setText(platform.getTitoloMinerario());
                centraleCollegata.setText(platform.getCentraleCollegata());
                zona.setText(platform.getZona());
                foglio.setText(platform.getFoglio());
                sezioneUnimig.setText(platform.getSezioneUnimig());
                capitaneriaDiPorto.setText(platform.getCapitaneriaDiPorto());
                dimensioni.setText(platform.getDimensioni());
                codice.setText(String.valueOf(platform.getCodice()));
                annoCostruzione.setText(String.valueOf(platform.getAnnoCostruzione()));
                pozziAllacciati.setText(String.valueOf(platform.getPozziAllacciati()));
                pozziProduttiviNonEroganti.setText(String.valueOf(platform.getPozziProduttiviNonEroganti()));
                pozziInProduzione.setText(String.valueOf(platform.getPozziInProduzione()));
                pozziInMonitoraggio.setText(String.valueOf(platform.getPozziInMonitoraggio()));
                distanzaCosta.setText(platform.getDistanzaCosta()+" km");
                altezza.setText(platform.getAltezza()+" m");
                profonditaFondale.setText(platform.getProfonditaFondale()+" m");

            }
        });


    }
}