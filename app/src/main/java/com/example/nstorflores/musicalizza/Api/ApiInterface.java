package com.example.nstorflores.musicalizza.Api;

import com.example.nstorflores.musicalizza.modelsAPI.AccessToken;
import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.example.nstorflores.musicalizza.modelsAPI.AlbumCreate;
import com.example.nstorflores.musicalizza.modelsAPI.Artist;
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;
import com.example.nstorflores.musicalizza.modelsAPI.Song;
import com.example.nstorflores.musicalizza.modelsAPI.User;
import com.example.nstorflores.musicalizza.modelsAPI.UserCreate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Néstor Flores on 2/3/2018.
 */
public interface ApiInterface {

    @GET("Songs?filter={\"include\":[\"song_genre\",\"song_album\"]}")
    Call<List<Song>> getSongs();

    @POST("Songs")
    Call<SongCreate> createSong(@Body SongCreate song);

    @GET("Songs/{id}?filter={\"include\":[\"song_genre\",\"song_album\"]}")
    Call<Song> getSong(@Path("id") int songId);

    //Album
    @GET("Albums?filter={\"include\":[\"album_genre\",\"album_image\",\"album_artist\"]}")
    Call<List<Album>> getAlbums();

    /*@GET("Albums?filter={\"where\":{\"type\":\"Album\"}}")
    Call<List<Album>> getFullAlbums();*/

    @GET("Albums")
    Call<List<Album>> getFullAlbums(@Query("filter") String filter);

    @POST("Albums")
    Call<Album> createAlbum(@Body AlbumCreate album);

    //Artists

    @GET("Artists")
    Call<List<Artist>> getArtists();


    //Login

    @POST("Users/login")
    Call<AccessToken> login(@Body User user);

    @POST("Users")
    Call<User> signUp(@Body UserCreate user);
}

