package it.univaq.offshoregasplatform;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseService extends IntentService {

    public static final String EXTRA_ACTION = "extra_action";
    public static final int ACTION_SAVE     = 0;
    public static final int ACTION_GET    = 1;

    public DatabaseService() {
        super("DatabaseService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int action = intent.getIntExtra(EXTRA_ACTION, -1);

        switch (action){
            case ACTION_SAVE:
                this.save(intent);
                break;
            case ACTION_GET:
                this.get(intent);
                break;
        }
    }

    private void save(Intent intent){
        ArrayList<GasPlatform> platforms = intent.getParcelableArrayListExtra("platforms");
        System.out.println("Numero di piattaforme dall'intent: "+ platforms.size());
        DataBase.getInstance(getApplicationContext()).gasPlatform_dao().save(platforms);
    }

    private void get(Intent intent){
        List<GasPlatform> platformList = DataBase.getInstance(getApplicationContext()).gasPlatform_dao().getAll();
        System.out.println("Numero di piattaforme dall'Database: "+ platformList.size());
    }

    private void UpdateFromHttp(Intent intent){
        
    }
}
