package com.example.nstorflores.musicalizza.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nstorflores.musicalizza.Activities.ShowLyricsActivity;
import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.modelsAPI.Song;
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;
import com.example.nstorflores.musicalizza.modelsDB.AlbumDb;
import com.example.nstorflores.musicalizza.modelsDB.AlbumSongsDb;
import com.example.nstorflores.musicalizza.modelsDB.SongsAlbumDb;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;


public class MyOfflineSongsAdapter extends RecyclerView.Adapter<MyOfflineSongsAdapter.ViewHolder> {

    public static final String SONG_ID = "songId";
    public static final String OFFLINE_LYRICS = "offlineLyrics";
    public static final String LYRICS = "lyrics";
    public static final String TITLE = "title";
    public static final String IMAGE_PATH = "imagePath";
    /*public static final String ARTIST_ID = "artistId";
    public static final String ALBUM_ID = "latSong";
    public static final String LONG_COMPLAINT = "longSong";
*/
       private List<SongsAlbumDb> songsAlbumDbs;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public CardView songCardView;
        public SimpleDraweeView imageSong;
        public TextView artistName;
        public ImageView moreImageButton;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            songCardView = view.findViewById(R.id.card_view);
            imageSong = view.findViewById(R.id.song_image);
            artistName = view.findViewById(R.id.artist_name);
            moreImageButton = view.findViewById(R.id.overflow);
        }
    }

       // Provide a suitable constructor (depends on the kind of dataset)
    public MyOfflineSongsAdapter(List<SongsAlbumDb> songsAlbumDbs) {
        this.songsAlbumDbs = songsAlbumDbs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyOfflineSongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyOfflineSongsAdapter.ViewHolder viewHolder = new MyOfflineSongsAdapter.ViewHolder(view);

        return viewHolder;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyOfflineSongsAdapter.ViewHolder holder, final int position) {
        final SongsAlbumDb songsAlbumDb = songsAlbumDbs.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try{

            holder.title.setText(songsAlbumDb.getSongDb().getTitle());
           // Log.i("Genre", albumSongsDb.getGenre().getName());
            holder.artistName.setText(songsAlbumDb.getSongDb().getTitle());

            String storagePath = "/imagenesMusicalizza/";
            String newNameImage = songsAlbumDb.getAlbumDb().get(0).getName().replaceAll("\\s+","-");
            final String photoPath = Environment.getExternalStorageDirectory() + "/"+ newNameImage + ".jpg";
            Log.i("direccion imagen", photoPath);
            //Bitmap bitmap = BitmapFactory.decodeFile(photoPath);

            holder.imageSong.setImageURI(Uri.fromFile(new File(photoPath)));

            //ImageView.setImageURI(Uri.fromFile(new File("/sdcard/cats.jpg")));

            /*Uri uri = Uri.parse(albumSongsDb.getAlbum().getImage().getUrl());

            holder.imageSong.setImageURI(uri);*/

            holder.songCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Log.i("click", "clikeado xd");
                        try {
                            Intent intent = new Intent(view.getContext(),ShowLyricsActivity.class);

                            intent.putExtra(SONG_ID,songsAlbumDb.getSongDb().getId());
                            intent.putExtra(OFFLINE_LYRICS,1);
                            intent.putExtra(LYRICS,songsAlbumDb.getSongDb().getLyric());
                            intent.putExtra(TITLE,songsAlbumDb.getSongDb().getTitle());
                            intent.putExtra(IMAGE_PATH,photoPath);
                            view.getContext().startActivity(intent);

                        }catch (Exception e){
                            Log.e("ERROR: ",e.getMessage());
                        }

                    }
            });


            /*holder.moreImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Click");
                        showPopupMenu(holder.moreImageButton,position,albumSongsDb);
                    }
                });
                if(moreButtonVisibility)
                {
                    holder.moreImageButton.setVisibility(View.VISIBLE);

                }
                else
                {
                    holder.moreImageButton.setVisibility(View.GONE);
                }*/
            
        }catch (Exception e){
            Log.e("ERROR_COMPLAINT: ",e.getMessage());
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return songsAlbumDbs.size();
    }



}