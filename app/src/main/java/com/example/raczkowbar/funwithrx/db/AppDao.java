package com.example.raczkowbar.funwithrx.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface AppDao {
    @Query("SELECT * FROM DbEntry")
    Flowable<List<DbEntry>> getDbEntryAllRx();

    @Insert
    void storeDbEntry(DbEntry dbEntry);
}
