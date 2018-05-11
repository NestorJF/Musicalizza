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

    @Delete()
    void deleteAllcancions(SongDb song);

    @Query("DELETE FROM SongDb where id = :songId")
    public void deleteSong(int songId);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertAll(SongDb songs);

    @Update
    void updateUser(SongDb song);

    @Query("DELETE FROM SongDb")
    public void deleteTable();

    @Query("SELECT * FROM SongDb")
    List<SongsAlbumDb> getSongsAlbumDb();

    @Query("Select * from SongDb where id = :songId")
    SongsAlbumDb getSong(int songId);

}
