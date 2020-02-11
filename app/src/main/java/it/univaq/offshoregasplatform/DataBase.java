package it.univaq.offshoregasplatform;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * SettimaLezione
 * Created by leonardo on 2019-11-15.
 */

@Database(entities = GasPlatform.class, version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract GasPlatform_DAO gasPlatform_dao();

    private static DataBase instance = null;

    public static DataBase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    DataBase.class,
                    "database.db")
                    .build();
        }
        return instance;
    }
}