package com.example.nstorflores.musicalizza.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nstorflores.musicalizza.modelsAPI.Image;
import com.example.nstorflores.musicalizza.modelsDB.ImageDb;

import java.util.List;

/**
 * Created by Néstor Flores on 30/4/2018.
 */
@Dao
public interface ImageDao {

    @Query("Select * from ImageDb")
    List<ImageDb> getGenres();

    @Query("Select * from ImageDb where id = :id")
    ImageDb getImage(int id);

    @Delete()
    void deleteAllImages(ImageDb image);

    @Insert
    void insertAll(ImageDb... image);

    @Update
    void updateImage(ImageDb image);
}
