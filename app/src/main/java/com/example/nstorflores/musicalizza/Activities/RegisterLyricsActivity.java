package com.example.nstorflores.musicalizza.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nstorflores.musicalizza.Api.Api;
import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.example.nstorflores.musicalizza.modelsAPI.AlbumCreate;
import com.example.nstorflores.musicalizza.modelsAPI.Artist;
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterLyricsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView typeAlbumText;
    private EditText title;
    private EditText lyrics;
    private Spinner albumType;
    private Spinner album;
    private Spinner artist;
    private Button registerLyrics;

    private List<Album> albums;
    private List<Artist> artists;

    private int artistId;
    private int albumId = 0;
    private int genreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lyrics);
        initViews();
        setArtistSpinnerOptions();
        setTypeAlbumSpinnerOptions();
        initButtonClick();
    }

    public void initViews()
    {
        title = findViewById(R.id.title_text);
        lyrics = findViewById(R.id.lyrics_text);
        albumType = findViewById(R.id.type_album_spinner);
        album = findViewById(R.id.album_spinner);
        artist = findViewById(R.id.artist_spinner);
        registerLyrics = findViewById(R.id.register_lyrics);
        typeAlbumText = findViewById(R.id.type_album_text);
    }

    public void setTypeAlbumSpinnerOptions()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_album_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        albumType.setAdapter(adapter);
        albumType.setOnItemSelectedListener(this);

    }

    public void setArtistSpinnerOptions()
    {
        Call<List<Artist>> call = Api.instance().getArtists();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Artist>> call, @NonNull Response<List<Artist>> response) {

                if (response.body() != null) {
                    artists = response.body();
                    Log.i("PROBLEMA", "no hay error alb " + response.body().get(0).getName());

                    ArrayAdapter<Artist> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, artists);

                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    artist.setAdapter(arrayAdapter);
                }
                else
                {
                    Log.i("PROBLEMA", "hay error: "+ response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Artist>> call, @NonNull Throwable t) {
                Log.e("Error xd",t.getMessage());
                //recyclerView.setAdapter(new OfflineAdapter("Revise su conexión a internet e intente nuevamente"));
                //swipeContainer.setRefreshing(false);
            }
        });

        artist.setOnItemSelectedListener(this);


    }

    public void setAlbumSpinnerOptions(int albumType)
    {
        String filter = "";
        switch (albumType)
        {
            case 1:
                typeAlbumText.setText(R.string.album);
                filter ="{\"where\":{\"type\":\"Album\",\"artist_id\":"+artistId+"}}";

                break;
            case 2:
                typeAlbumText.setText(R.string.register_lyrics_ep_text);
                filter ="{\"where\":{\"type\":\"EP\",\"artist_id\":"+artistId+"}}";
                break;
        }
        Log.i("FILTERA: ", filter);


        if(!filter.isEmpty())
        {
            Call<List<Album>> call = Api.instance().getFullAlbums(filter);
            call.enqueue(new Callback<List<Album>>() {
                @Override
                public void onResponse(@NonNull Call<List<Album>> call, @NonNull Response<List<Album>> response) {

                    if (response.body() != null) {
                        try
                        {
                            albums = response.body();
                            Log.i("PROBLEMA", "no hay error albumspiner " + response.body().get(0).getName());

                            ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_spinner_dropdown_item, albums);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            album.setAdapter(arrayAdapter);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"El artista no tiene la discografía seleccionada", Toast.LENGTH_LONG).show();

                        }

                    }
                    else
                    {
                        Log.i("PROBLEMA", "hay error: "+ response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Album>> call, @NonNull Throwable t) {
                    Log.e("Error xd",t.getMessage());
                    //recyclerView.setAdapter(new OfflineAdapter("Revise su conexión a internet e intente nuevamente"));
                    //swipeContainer.setRefreshing(false);
                }
            });
        }
        else



        album.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.artist_spinner)
        {

            Artist artist = (Artist) parent.getItemAtPosition(position);
            artistId = artist.getId();
            Log.i("POS: ", "artista id spinner: "+ artistId + " album type selec: " + albumType.getSelectedItemPosition());

            if(album.getSelectedItem() != null)
            {
                Log.i("POS: ", "asdxd");
                Log.i("POS: ", "tipo album en spinner al artist "+ albumType.getSelectedItemPosition());

                setAlbumSpinnerOptions(albumType.getSelectedItemPosition());
            }
        }

        if(parent.getId() == R.id.type_album_spinner)
        {
            Log.i("POS: ", "tipo album spinner: "+ position );
            setAlbumSpinnerOptions(position);
        }

        if(parent.getId() == R.id.album_spinner)
        {

            Album album = (Album) parent.getItemAtPosition(position);
            albumId = album.getId();
            genreId = album.getGenreId();
            Log.i("POS: ", "album spinner: "+ position +" album id :"+ albumId );

        }
        //setAlbumSpinnerOptions(position);

    }

    private void initButtonClick()
    {
        registerLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(!title.getText().toString().isEmpty() && !lyrics.getText().toString().isEmpty())
                {
                    if (albumId == 0)
                    {
                        createSingleAlbum();
                    }
                    else
                    {
                        createSong();
                    }
                }

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createSong()
    {

        SongCreate song = new SongCreate();
        song.setTitle(title.getText().toString());
        song.setLyric(lyrics.getText().toString());

        Log.i("ALWE", " albumid al insertar: "+ albumId);
        song.setAlbumId(albumId);
        song.setArtistId(artistId);
        song.setGenreId(genreId);

        Call<SongCreate> call = Api.instance().createSong(song);

        call.enqueue(new Callback<SongCreate>() {
            @Override
            public void onResponse(Call<SongCreate> call, Response<SongCreate> response) {
                if (response.body() != null) {

                    SongCreate complaintResult  = response.body();
                    //complaintId = complaintResult.getId();
                    assert complaintResult != null;


                    /*Snackbar.make(view, "Letra de canción agregada con éxito", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    finish();
                }
                else
                {
                    Log.i("PROBLEMA CREATE", "hay error: "+ response.code());

                }
            }

            @Override
            public void onFailure(Call<SongCreate> call, Throwable t) {

                Log.e(getString(R.string.error_message),t.getMessage());
            }
        });
    }

    private void createSingleAlbum()
    {

        AlbumCreate album = new AlbumCreate();
        album.setArtistId(artistId);
        album.setGenreId(genreId);
        album.setImageId(1);
        album.setType("Single");
        album.setYear(2018);
        album.setName(title.getText().toString() +" (Single)");

        Call<Album> call = Api.instance().createAlbum(album);

        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.body() != null) {

                    Album albumResult  = response.body();
                    albumId = albumResult.getId();
                    Log.i("ALWE", "album result id: " + albumResult.getId() +" albumid: "+ albumId);
                    assert albumResult != null;
                    createSong();
                }
                else
                {
                    Log.i("PROBLEMA Single ALBUM", "hay error: "+ response.code());

                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

                Log.e(getString(R.string.error_message),t.getMessage());
            }
        });
    }
}
