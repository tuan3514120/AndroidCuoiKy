package com.example.covi.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.covi.KBYT;

@Database(entities = {KBYT.class,}, version = 1)
public abstract class DB extends RoomDatabase {

    private static final String DATABASE_NAME = "android.db";
    private static DB instance;

    public static synchronized DB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
            return instance;
    }
    public abstract DAO DAO();

}
