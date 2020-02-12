package it.univaq.offshoregasplatform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient locationClient;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(DatabaseService.FILTER_GET)) {
                GasPlatformViewModel provider = ViewModelProviders.of(MainActivity.this).get(GasPlatformViewModel.class);
                ArrayList<GasPlatform> platforms = intent.getParcelableArrayListExtra("platforms");
                provider.setPlatforms(platforms);

                //Aggiorno le piattaforme vicine
                calculateNearLocations();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        locationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_main);

        GasPlatformViewModel provider = ViewModelProviders.of(this).get(GasPlatformViewModel.class);

        //Chiamo il Database Service per aggiornare la lista delle piattaforme nel provider
        Intent intent = new Intent(MainActivity.this, DatabaseService.class);
        intent.putExtra(DatabaseService.EXTRA_ACTION, DatabaseService.ACTION_GET);
        startService(intent);

    }


    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, new IntentFilter(DatabaseService.FILTER_GET));
        //Chiamo il Database Service per aggiornare la lista delle piattaforme nel provider
        Intent intent = new Intent(MainActivity.this, DatabaseService.class);
        intent.putExtra(DatabaseService.EXTRA_ACTION, DatabaseService.ACTION_GET);
        startService(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("pausa");
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(receiver);
    }

    protected void calculateNearLocations(){

        final GasPlatformViewModel provider = ViewModelProviders.of(MainActivity.this).get(GasPlatformViewModel.class);
        final ArrayList<GasPlatform> piattaforme = provider.getPlatforms().getValue();
        System.out.println("Nel ViewModel ci sono: "+piattaforme.size()+" piattaforme");
        //Ottengo la location dello smartphone
        locationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                ArrayList<GasPlatform> piattaformeVicine = new ArrayList<>();
                //Per ogni piattaforma presente nel ViewModel calcolo la distanza dall'utente
                for (GasPlatform plt: piattaforme){
                    //Calcolo la distanza fra la location dello smartphone e la piattaforme in questione
                    float[] results = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), plt.getLatitudine(), plt.getLongitudine(), results);
                    //Converto la distanza da metri a chilometri
                    float distanzaInKm = results[0]/1000;
                    //System.out.println("Distanza dalla piattaforma "+plt.getDenominazione()+": "+distanzaInKm+" km");
                    //Se la piattaforma è nel raggio di 100km allora la inserisco nelle piattaforme vicine
                    if(distanzaInKm <= 100){
                        piattaformeVicine.add(plt);
                    }
                }
                System.out.println("Nel ViewModel ci sono: "+piattaformeVicine.size()+" piattaforme vicine");
                provider.setNearPlatforms(piattaformeVicine);
            }
        });
    }
}
