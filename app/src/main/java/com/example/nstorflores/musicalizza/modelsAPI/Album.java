package com.example.nstorflores.musicalizza.modelsAPI;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Néstor Flores on 29/4/2018.
 */
public class Album {

    private int id;
    private String name;
    private int year;
    private String type;

    @SerializedName("image_id")
    private int imageId;

    @SerializedName("artist_id")
    private int artistId;

    @SerializedName("genre_id")
    private int genreId;

    @SerializedName("album_artist")
    private Artist artist;

    @SerializedName("album_genre")
    private Genre genre;

    @SerializedName("album_image")
    private Image image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


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

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}
