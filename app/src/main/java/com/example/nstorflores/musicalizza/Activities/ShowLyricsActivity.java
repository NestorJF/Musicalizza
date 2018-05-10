package com.example.nstorflores.musicalizza.Activities;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nstorflores.musicalizza.Adapters.LyricsAdapter;
import com.example.nstorflores.musicalizza.Adapters.MyOfflineSongsAdapter;
import com.example.nstorflores.musicalizza.Adapters.SongsAdapter;
import com.example.nstorflores.musicalizza.Api.Api;
import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.db.AppDatabase;
import com.example.nstorflores.musicalizza.modelsAPI.Song;
import com.example.nstorflores.musicalizza.modelsDB.AlbumDb;
import com.example.nstorflores.musicalizza.modelsDB.ArtistDb;
import com.example.nstorflores.musicalizza.modelsDB.SongDb;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowLyricsActivity extends AppCompatActivity {

    private int songId;
    private int offlineLyrics;

    private TextView artist;
    private TextView songName;
    private SimpleDraweeView discImage;
    private ImageView downloadSong;

    private RecyclerView recyclerView;

    private Song song = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lyrics);
        initView ();
        initButtonClick();
    }

    private void initView () {
        songName = findViewById(R.id.name_song);
        artist = findViewById(R.id.autor);
        recyclerView = findViewById(R.id.lyrics_recycler_view);
        discImage = findViewById(R.id.disc_image);
        downloadSong = findViewById(R.id.download_song);

        songId = getIntent().getIntExtra(SongsAdapter.SONG_ID,0);
        offlineLyrics = getIntent().getIntExtra(MyOfflineSongsAdapter.OFFLINE_LYRICS,0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        try {
            getSong();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSong()
    {

        if(offlineLyrics == 0)
        {
            Call<Song> call = Api.instance().getSong(songId);

            call.enqueue(new Callback<Song>() {
                @Override
                public void onResponse(@NonNull Call<Song> call, Response<Song> response) {

                    if(response.body() != null){

                        try{
                            song = response.body();
                            songName.setText(response.body().getTitle());
                            Log.i("artista", response.body().getAlbum().getImage().getUrl());
                            artist.setText(response.body().getAlbum().getArtist().getName());

                            Log.i("Imagen", response.body().getAlbum().getImage().getUrl());
                            Uri uri = Uri.parse(response.body().getAlbum().getImage().getUrl());
                            discImage.setImageURI(uri);

                            setLyricsToAdapter(response.body().getLyric());
                            String newNameImage = song.getAlbum().getName().replaceAll("\\s+","-");
                            Log.i("Nombre imagen", newNameImage);

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"No hay internet", Toast.LENGTH_LONG).show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<Song> call, Throwable t) {
                    Log.e(getString(R.string.error_message), t.getMessage());
                }
            });
        }
        else
        {

            String imagePath = getIntent().getStringExtra(MyOfflineSongsAdapter.IMAGE_PATH);
            discImage.setImageURI(Uri.fromFile(new File(imagePath)));

            String title = getIntent().getStringExtra(MyOfflineSongsAdapter.TITLE);
            songName.setText(title);

            String lyrics = getIntent().getStringExtra(MyOfflineSongsAdapter.LYRICS);
            setLyricsToAdapter(lyrics);
            Toast.makeText(getApplicationContext(),"No hay internet", Toast.LENGTH_LONG).show();


        }

    }

    private void setLyricsToAdapter(String lyrics)
    {
        String salto = "\n\n";
        String [] paragraphs = lyrics.split(salto);
        Log.i("Salto", ""+paragraphs.length);
        for(int i= 0; i< paragraphs.length;i++)
        {
            Log.i("Salto", paragraphs[i]);
        }

        LyricsAdapter lyricsAdapter = new LyricsAdapter(paragraphs);
        recyclerView.setAdapter(lyricsAdapter);
    }

    private void initButtonClick()
    {
        downloadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "Musicalizza").allowMainThreadQueries().fallbackToDestructiveMigration().build();

                db.songDao().deleteTable();
                db.artistDao().deleteTable();
                db.albumDao().deleteTable();


                ArtistDb artistDb = new ArtistDb();
                artistDb.setName(song.getAlbum().getArtist().getName());
                artistDb.setAge(song.getAlbum().getArtist().getAge());
                artistDb.setBiography(song.getAlbum().getArtist().getBiography());
                artistDb.setCountry(song.getAlbum().getArtist().getCountry());
                artistDb.setGenreId(song.getAlbum().getArtist().getGenreId());
                artistDb.setImageId(song.getAlbum().getArtist().getImageId());

                long insertedArtistId = db.artistDao().insertAll(artistDb);

                Log.i("Artista guardado:", "id " + insertedArtistId + ", name: "+ artistDb.getName());


                AlbumDb albumDb = new AlbumDb();
                albumDb.setArtistId((int) insertedArtistId);
                albumDb.setGenreId(song.getAlbum().getGenreId());
                albumDb.setImageId(song.getAlbum().getImageId());
                albumDb.setName(song.getAlbum().getName());
                albumDb.setType(song.getAlbum().getType());
                albumDb.setYear(song.getAlbum().getYear());

                long insertedAlbumId = db.albumDao().insertAll(albumDb);

                Log.i("Album guardado:", "id " + insertedAlbumId + ", name: "+ albumDb.getName() + "artistaId: "+ albumDb.getArtistId());



                SongDb songDb = new SongDb();
                songDb.setTitle(song.getTitle());
                songDb.setLyric(song.getLyric());
                songDb.setGenreId(song.getGenreId());
                songDb.setAlbumId((int) insertedAlbumId);
                songDb.setArtistId(song.getArtistId());

                long insertedSongid = db.songDao().insertAll(songDb);

                SongDb songResultado = db.songDao().getSong(songDb.getTitle());
                Log.i("Cancion guardada", "id: "+ insertedSongid + " title " + songResultado.getTitle() + "albumId: "+ insertedAlbumId + ", letra: "+ songResultado.getLyric());

                /*Toast.makeText(getBaseContext(), "Se han guardado y mostrado los datos con éxito",
                        Toast.LENGTH_LONG).show();*/

                requestPermissions();
                try {
                    saveImageOnStorage(song.getAlbum().getImage().getUrl(), song.getAlbum().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveImageOnStorage(String urlImage, String nameImage) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = new URL (urlImage);
        String newNameImage = nameImage.replaceAll("\\s+","-");
        String storagePathImage = "/imagenesMusicalizza/";
        InputStream input = url.openStream();
        try {
            File storagePath = Environment.getExternalStorageDirectory();
            OutputStream output = new FileOutputStream(storagePath+ "/"+ newNameImage+ ".jpg");
            try {
                byte[] buffer = new byte[1000];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                    output.write(buffer, 0, bytesRead);
                }
                Toast.makeText(getBaseContext(), "La imagen se ha guardado con éxito",
                        Toast.LENGTH_LONG).show();
            }
            finally {
                output.close();
            }
        } finally {
            input.close();
        }
    }

    private void requestPermissions() {
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage(getString(R.string.without_permits))
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

}
