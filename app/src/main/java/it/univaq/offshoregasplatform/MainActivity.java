package it.univaq.offshoregasplatform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(DatabaseService.FILTER_GET)) {
                ArrayList<GasPlatform> platforms = intent.getParcelableArrayListExtra("platforms");
                GasPlatformViewModel provider = ViewModelProviders.of(MainActivity.this).get(GasPlatformViewModel.class);
                provider.setPlatforms(platforms);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Test
        super.onCreate(savedInstanceState);

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
}
