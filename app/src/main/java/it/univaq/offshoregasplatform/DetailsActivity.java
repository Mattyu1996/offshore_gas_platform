package it.univaq.offshoregasplatform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView stato = findViewById(R.id.valore_stato);
        TextView tipo = findViewById(R.id.valore_tipo);
        TextView minerale = findViewById(R.id.valore_minerale);
        TextView operatore = findViewById(R.id.valore_operatore);
        TextView titoloMinerario = findViewById(R.id.valore_titoloMinerario);
        TextView centraleCollegata = findViewById(R.id.valore_centraleCollegata);
        TextView zona = findViewById(R.id.valore_zona);
        TextView foglio = findViewById(R.id.valore_foglio);
        TextView sezioneUnimig = findViewById(R.id.valore_sezioneUnimig);
        TextView capitaneriaDiPorto = findViewById(R.id.valore_capitaneriaDiPorto);
        TextView dimensioni = findViewById(R.id.valore_dimensioni);
        TextView codice = findViewById(R.id.valore_codice);
        TextView annoCostruzione = findViewById(R.id.valore_annoCostruzione);
        TextView pozziAllacciati = findViewById(R.id.valore_pozziAllacciati);
        TextView pozziProduttiviNonEroganti = findViewById(R.id.valore_pozziProduttiviNonEroganti);
        TextView pozziInProduzione = findViewById(R.id.valore_pozziInProduzione);
        TextView pozziInMonitoraggio = findViewById(R.id.valore_pozziInMonitoraggio);
        TextView distanzaCosta = findViewById(R.id.valore_distanzaCosta);
        TextView altezza = findViewById(R.id.valore_altezza);
        TextView profonditaFondale = findViewById(R.id.valore_profonditaFondale);

        //Ottengo l'intent
        Intent intent = getIntent();
        //Ottengo la piattaforma dall'intent
        GasPlatform platform = intent.getParcelableExtra("platform");
        if(platform != null){
            //Imposto la denominazione della piattaforma come titolo dell'activity
            toolbar.setTitle(platform.getDenominazione());
            //Imposto tutti i dettagli della piattaforma
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

        setSupportActionBar(toolbar);


    }

}
