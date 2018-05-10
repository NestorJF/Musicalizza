package com.example.nstorflores.musicalizza.modelsAPI;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Néstor Flores on 2/5/2018.
 */

public class Song {

    private int id;
    private String title;
    private String lyric;

    @SerializedName("artist_id")
    private int artistId;

    @SerializedName("album_id")
    private int albumId;

    @SerializedName("genre_id")
    private int genreId;

    @SerializedName("song_album")
    private Album album;

    @SerializedName("song_genre")
    private Genre genre;

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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


}
