package com.example.nstorflores.musicalizza.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.example.nstorflores.musicalizza.modelsDB.AlbumDb;
import com.example.nstorflores.musicalizza.modelsDB.AlbumSongsDb;

import java.util.List;

/**
 * Created by Néstor Flores on 30/4/2018.
 */
@Dao
public interface AlbumDao {

    @Query("Select * from AlbumDb")
    List<AlbumDb> getAlbums();

    @Query("Select * from AlbumDb where id = :id")
    AlbumDb getAlbum(int id);


    @Delete()
    void deleteAllAlbums(AlbumDb album);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertAll(AlbumDb album);

    @Update
    void updateAlbum(AlbumDb album);

    @Query("DELETE FROM AlbumDb")
    public void deleteTable();

    @Query("SELECT * FROM AlbumDb")
    List<AlbumSongsDb> getAlbumSongs();
}
