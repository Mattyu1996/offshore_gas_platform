package it.univaq.offshoregasplatform;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GasPlatform_DAO {

@Query("SELECT * FROM trivelle ORDER BY denominazione ASC")
    public List<GasPlatform> getAll();

@Insert(onConflict = OnConflictStrategy.REPLACE)
    public List<Long> save(List<GasPlatform> gasPlatforms);
}
