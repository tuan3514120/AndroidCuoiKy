package com.example.covi.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.covi.KBYT;
import java.util.List;
@Dao
public interface DAO {
    @Insert
    void insert(KBYT kbyt);
    @Query("SELECT*FROM android")
    List<KBYT>getListKBYT();
    @Query("SELECT*FROM android where phone =:phone")
    List<KBYT>checkKbyt(String phone);
    @Update
    void updateKbyt(KBYT kbyt);
    @Delete
    void deleteKbyt(KBYT kbyt);
    @Query("DELETE FROM android")
    void deleteAll();
    @Query("SELECT*FROM android WHERE name LIKE '%'|| :name || '%'")
    List<KBYT>search(String name);
}
