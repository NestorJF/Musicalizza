package com.example.nstorflores.musicalizza.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nstorflores.musicalizza.modelsAPI.Song;
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;
import com.example.nstorflores.musicalizza.modelsDB.AlbumSongsDb;
import com.example.nstorflores.musicalizza.modelsDB.SongDb;
import com.example.nstorflores.musicalizza.modelsDB.SongsAlbumDb;

import java.util.List;

/**
 * Created by User on 12/04/2018.
 */

@Dao
public interface SongDao {
    @Query("Select * from SongDb where title like :nombre")
    SongDb getSong(String nombre);


    @Delete()
    void deleteAllcancions(SongDb song);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertAll(SongDb songs);

    @Update
    void updateUser(SongDb song);

    @Query("DELETE FROM SongDb")
    public void deleteTable();

    @Query("SELECT * FROM SongDb")
    List<SongsAlbumDb> getSongsAlbumDb();

}
