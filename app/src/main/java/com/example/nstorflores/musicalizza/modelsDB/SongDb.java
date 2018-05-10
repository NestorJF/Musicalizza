package com.example.nstorflores.musicalizza.modelsDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.example.nstorflores.musicalizza.modelsAPI.Genre;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Néstor Flores on 8/5/2018.
 */
@Entity
public class SongDb {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String lyric;

    @ColumnInfo(name = "artist_id")
    private int artistId;

    @ColumnInfo(name = "album_id")
    private int albumId;

    @ColumnInfo(name = "genre_id")
    private int genreId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }



}
