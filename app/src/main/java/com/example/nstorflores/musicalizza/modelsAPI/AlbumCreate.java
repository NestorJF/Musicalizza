package com.example.nstorflores.musicalizza.modelsAPI;

import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Néstor Flores on 29/4/2018.
 */

public class AlbumCreate {


    //private int id;

    private String name;

    private int year;

    @PrimaryKey
    private String type;

    @SerializedName("image_id")
    private int imageId;

    @SerializedName("artist_id")
    private int artistId;

    @SerializedName("genre_id")
    private int genreId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }


    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
}
