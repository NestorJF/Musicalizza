package com.example.nstorflores.musicalizza.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nstorflores.musicalizza.modelsAPI.Artist;
import com.example.nstorflores.musicalizza.modelsDB.ArtistDb;

import java.util.List;

/**
 * Created by Néstor Flores on 30/4/2018.
 */
@Dao
public interface ArtistDao {

    @Query("Select * from ArtistDb")
    List<ArtistDb> getArtists();

    @Query("Select * from ArtistDb where id = :id")
    ArtistDb getArtist(int id);

    @Delete()
    void deleteAllArtists(ArtistDb artist);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertAll(ArtistDb artist);

    @Update
    void updateArtist(ArtistDb artist);

    @Query("DELETE FROM ArtistDb")
    public void deleteTable();
}
