package com.example.nstorflores.musicalizza.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nstorflores.musicalizza.Adapters.AlbumsAdapter;
import com.example.nstorflores.musicalizza.Api.Api;
import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.modelsAPI.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Album> albums;
    private AlbumsAdapter albumsAdapter = null;



    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        init(view);

        return view;
    }

    private void init(View view) {

        recyclerView = view.findViewById(R.id.album_recycler_view);
        //recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(new AdapterProgress());

        try {
            getAlbums();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAlbums() {
        Call<List<Album>> call = Api.instance().getAlbums();
        //Remember.getString(getString(R.string.key_access_token),"")
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(@NonNull Call<List<Album>> call, @NonNull Response<List<Album>> response) {

                if (response.body() != null) {
                    try
                    {
                        albums = response.body();
                        Log.i("PROBLEMA", "no hay error alb " + response.body().get(0).getName());
                        Log.i("PROBLEMA", "no hay error alb " + response.body().get(0).getArtist().getName());
                        Log.i("PROBLEMA", "no hay error alb " + response.body().get(0).getGenre().getName());
                        albumsAdapter = new AlbumsAdapter(albums);
                        recyclerView.setAdapter(albumsAdapter);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(),"No hay álbumes registrados", Toast.LENGTH_LONG).show();

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

}
