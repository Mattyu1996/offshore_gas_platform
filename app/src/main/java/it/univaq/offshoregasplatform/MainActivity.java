package it.univaq.offshoregasplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Test
        super.onCreate(savedInstanceState);
        //gianfranco
        setContentView(R.layout.activity_main);
        ArrayList g = new ArrayList<GasPlatform>();
        GasPlatform gs = new GasPlatform();
        gs.setAltezza(180);
        gs.setAnnoCostruzione(2020);
        gs.setCapitaneriaDiPorto("capitaneria");
        gs.setCentraleCollegata("pes");
        gs.setCodice(5050);
        gs.setDenominazione("pasqualone");
        gs.setDimensioni("tanto");
        gs.setDistanzaCosta(10);
        gs.setFoglio("un tot");
        gs.setLatitudine(55.99);
        gs.setLongitudine(56.99);
        gs.setMinerale("quarzo");
        gs.setOperatore("giggi");
        gs.setPozziAllacciati(4);
        gs.setPozziInMonitoraggio(2);
        gs.setPozziInProduzione(6);
        gs.setPozziProduttiviNonEroganti(0);
        gs.setProfonditaFondale(8);
        gs.setSezioneUnimig("non so di che parli");
        gs.setStato("italia");
        gs.setTipo("forza pescara");
        gs.setTitoloMinerario("tosto");
        gs.setZona("colliwood");

        DataBase.getInstance(this).gasPlatform_dao().save(g);

    }
}
