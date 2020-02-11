package it.univaq.offshoregasplatform;

import androidx.room.Query;

import java.util.List;

public interface GasPlatform_DAO {

@Query("SELECT * FROM trivelle ORDER BY denominazione ")
    public List<GasPlatform> getAll();
}
