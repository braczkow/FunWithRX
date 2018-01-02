package com.example.raczkowbar.funwithrx.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DbEntry.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract AppDao appDao();
}