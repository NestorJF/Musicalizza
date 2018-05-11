package com.example.nstorflores.musicalizza.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nstorflores.musicalizza.Api.Api;
import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.example.nstorflores.musicalizza.modelsAPI.AlbumCreate;
import com.example.nstorflores.musicalizza.modelsAPI.Artist;
import com.example.nstorflores.musicalizza.modelsAPI.Image;
import com.example.nstorflores.musicalizza.modelsAPI.ImageCreate;
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    private ImageButton addImage;

    private List<Album> albums;
    private List<Artist> artists;

    private int artistId;
    private int albumId = 0;
    private int genreId;

    private final int PICK_IMAGE_REQUEST = 71;

    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lyrics);
        initViews();
        setArtistSpinnerOptions();
        setTypeAlbumSpinnerOptions();
        initButtonClick();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
        addImage = findViewById(R.id.add_image);
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

            if(position > 0)
            {
                setAlbumSpinnerOptions(position);
                addImage.setVisibility(View.INVISIBLE);
            }
            else
            {
                addImage.setVisibility(View.VISIBLE);
                album.setAdapter(null);
                albumId = 0;
            }


        }

        if(parent.getId() == R.id.album_spinner)
        {

            Album albumSelected = (Album) parent.getItemAtPosition(position);
            albumId = albumSelected.getId();
            genreId = albumSelected.getGenreId();
            Log.i("POS: ", "album spinner: "+ position +" album id :"+ albumId );

        }
        //setAlbumSpinnerOptions(position);

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            uuid = UUID.randomUUID().toString();
            final StorageReference ref = storageReference.child("images/"+ uuid);

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterLyricsActivity.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                            {
                                @Override
                                public void onSuccess(Uri downloadUrl)
                                {
                                    Log.i("direccion firebase", String.valueOf(downloadUrl));
                                    createSingleImage(String.valueOf(downloadUrl));
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterLyricsActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Subiendo "+(int)progress+"%");
                        }
                    });


        }


    }

    private void initButtonClick()
    {
        registerLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(!title.getText().toString().isEmpty() && !lyrics.getText().toString().isEmpty())
                {
                        Log.i("album id al seleccionar", ""+albumId);
                        if (albumId == 0)
                        {
                            if(filePath!=null)
                            {
                                uploadImage();
                            }
                            else
                            {
                                Toast.makeText(RegisterLyricsActivity.this, "No ha seleccionado una imagen para el ", Toast.LENGTH_SHORT).show();

                            }
                        /*storage = FirebaseStorage.getInstance();
                        storageReference = storage.getReference();

                        StorageReference refUrl = storageReference.child("images/"+ uuid);*/
                        }
                        else
                        {
                            createSong();
                        }
                    }
                }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
               chooseImage();
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

    private void createSingleAlbum(int imageId)
    {

        AlbumCreate album = new AlbumCreate();
        album.setArtistId(artistId);
        album.setGenreId(1);
        album.setImageId(imageId);
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

    private void createSingleImage(String url)
    {

        ImageCreate image = new ImageCreate();

        image.setUrl(url);
        image.setType("Disc");

        Call<Image> call = Api.instance().createImage(image);

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (response.body() != null) {

                    Image imageResult  = response.body();
                    Log.i("ALWE", "image result id: " + imageResult.getId());
                    assert imageResult != null;
                    createSingleAlbum(imageResult.getId());
                }
                else
                {
                    Log.i("PROBLEMA Single ALBUM", "hay error: "+ response.code());

                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

                Log.e(getString(R.string.error_message),t.getMessage());
            }
        });
    }
}
