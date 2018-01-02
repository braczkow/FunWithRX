package com.example.raczkowbar.funwithrx.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DbEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "info")
    public String info;
}
