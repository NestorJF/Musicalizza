package com.example.nstorflores.musicalizza.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nstorflores.musicalizza.modelsAPI.Genre;
import com.example.nstorflores.musicalizza.modelsDB.GenreDb;

import java.util.List;

/**
 * Created by Néstor Flores on 30/4/2018.
 */
@Dao
public interface GenreDao {

    @Query("Select * from GenreDb")
    List<GenreDb> getGenres();

    @Query("Select * from GenreDb where id = :id")
    Genre getGenre(int id);

    @Delete()
    void deleteAllGenres(GenreDb genre);

    @Insert
    void insertAll(GenreDb... Genres);

    @Update
    void updateGenre(GenreDb Genres);

}
