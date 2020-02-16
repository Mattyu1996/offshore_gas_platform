package it.univaq.offshoregasplatform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;



public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_LOCATION = 17;

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
        GasPlatformViewModel provider = ViewModelProviders.of(this).get(GasPlatformViewModel.class);

        setContentView(R.layout.activity_main);

        //Chiamo il Database Service per aggiornare la lista delle piattaforme nel provider
        Intent intent = new Intent(MainActivity.this, DatabaseService.class);
        intent.putExtra(DatabaseService.EXTRA_ACTION, DatabaseService.ACTION_GET);
        startService(intent);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, new FragmentGasPlatform(), "platforms")
                .commit();

    }

    public void setMyFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("nogps");
        if(fragment != null){
            DialogFragment dialog = (DialogFragment) fragment;
            dialog.dismiss();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.exit :
                new ExitDialog().show(getSupportFragmentManager(),"exit");
                break;
            case R.id.listView :
                //Setto il fragmentList
                System.out.println("Lista");
                setMyFragment(new FragmentGasPlatform());
                break;
            case R.id.mapView :
                //Setto il fragmentMaps
                System.out.println("Mappa");
                setMyFragment(new FragmentMaps());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void calculateNearLocations(){
        final GasPlatformViewModel provider = ViewModelProviders.of(MainActivity.this).get(GasPlatformViewModel.class);
        final ArrayList<GasPlatform> piattaforme = provider.getPlatforms().getValue();
        System.out.println("Nel ViewModel ci sono: "+piattaforme.size()+" piattaforme");
        //Controllo se l'app ha il permesso della geolocalizzazione
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            //Controllo se il gps è acceso
            final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean isEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isEnabled){
                //creo la richiesta per la localizzazione
                final LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setInterval(60000);
                locationRequest.setFastestInterval(30000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                //creo la funzione di callback da utilizzare nell'aggiornamento della posizione
                final LocationCallback locationCallback = new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationresult){
                        if(locationresult == null){
                            return;
                        }
                        for (Location location : locationresult.getLocations()) {
                            if (location != null) {
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
                                    provider.setNearPlatforms(piattaformeVicine);
                                    System.out.println("Nel ViewModel ci sono: "+provider.getNearPlatforms().getValue().size()+" piattaforme vicine");
                                }
                            }
                        }
                    }
                };
                //aggiorno la posizione
                locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                //se il gps è disattivato mostro il dialog per l'attivazione
                System.out.println("il gps non è attivo");
                new EnableGPSdialog().show(getSupportFragmentManager(),"nogps");
            }
        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously*
                new PermissionDialog().show(getSupportFragmentManager(),"permission");
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                //controllo la risposta alla richiesta del permesso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permesso garantito
                    System.out.println("Permesso Garantito");
                    //Ottengo il dialogo dal tag e lo chiudo
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("permission");
                    if(fragment != null){
                        DialogFragment dialog = (DialogFragment) fragment;
                        dialog.dismiss();
                    }

                } else {
                    // Permesso rifiutato
                    System.out.println("Permesso Rifiutato");
                }
                return;
            }
        }
    }

}
