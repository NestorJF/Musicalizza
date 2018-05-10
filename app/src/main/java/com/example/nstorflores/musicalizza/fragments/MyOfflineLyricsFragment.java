package com.example.nstorflores.musicalizza.fragments;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nstorflores.musicalizza.Adapters.MyOfflineSongsAdapter;
import com.example.nstorflores.musicalizza.Adapters.SongsAdapter;
import com.example.nstorflores.musicalizza.Api.Api;
import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.db.AppDatabase;
import com.example.nstorflores.musicalizza.modelsAPI.Song;
import com.example.nstorflores.musicalizza.modelsDB.AlbumSongsDb;
import com.example.nstorflores.musicalizza.modelsDB.SongsAlbumDb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOfflineLyricsFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Song> songs;
    private SongsAdapter songsAdapter = null;

    public MyOfflineLyricsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_offline_lyrics, container, false);
        // Inflate the layout for this fragment
        /*if (savedInstanceState == null) {
            insertTabs(container);
        }*/

        init(view);

        return view;
    }

    private void init(View view) {

        recyclerView = view.findViewById(R.id.offine_lyrics_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(layoutManager);


        try {
            getSongs();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSongs() {

        AppDatabase db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "Musicalizza").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        List<SongsAlbumDb> songsAlbumDbs = db.songDao().getSongsAlbumDb();



        Log.i("AlbumSongs", "name song: "+songsAlbumDbs.get(0).getSongDb().getTitle());

        MyOfflineSongsAdapter myOfflineSongsAdapter = new MyOfflineSongsAdapter(songsAlbumDbs);
        recyclerView.setAdapter(myOfflineSongsAdapter);
        /*for(int i = 0; i < albumSongsDbs.size(); i++)
        {
            Log.i("AlbumSongs", "name: "+albumSongsDbs.get(i).songs.get(0).getTitle());
        }

        Call<List<Song>> call = Api.instance().getSongs();
        //Remember.getString(getString(R.string.key_access_token),"")
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {

                if (response.body() != null) {
                    songs = response.body();
                    Log.i("PROBLEMA", "no hay error " + response.body().get(0).getAlbum().getName());
                    Log.i("PROBLEMA", "no hay error " + response.body().get(0).getAlbum().getArtist().getName());
                    Log.i("PROBLEMA", "no hay error " + response.body().get(0).getGenre().getName());
                    songsAdapter = new SongsAdapter(songs);
                    recyclerView.setAdapter(songsAdapter);
                }
                else
                {
                    Log.i("PROBLEMA", "hay error: "+ response.code());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {
                Log.e("Error xd",t.getMessage());
                //recyclerView.setAdapter(new OfflineAdapter("Revise su conexión a internet e intente nuevamente"));
                //swipeContainer.setRefreshing(false);
            }
        });*/

    }

}
