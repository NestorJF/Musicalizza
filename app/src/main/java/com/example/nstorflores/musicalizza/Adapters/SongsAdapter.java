package com.example.nstorflores.musicalizza.Adapters;

import android.content.Intent;
import android.net.Uri;
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
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;
import com.example.nstorflores.musicalizza.modelsAPI.Song;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    public static final String SONG_ID = "songId";
    /*public static final String ARTIST_ID = "artistId";
    public static final String ALBUM_ID = "latSong";
    public static final String LONG_COMPLAINT = "longSong";
*/
       private List<Song> songs;


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
    public SongsAdapter(List<Song> songs) {
        this.songs = songs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        SongsAdapter.ViewHolder viewHolder = new SongsAdapter.ViewHolder(view);

        return viewHolder;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final SongsAdapter.ViewHolder holder, final int position) {
        final Song song = songs.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try{

            holder.title.setText(song.getTitle());
           // Log.i("Genre", song.getGenre().getName());
            holder.artistName.setText(song.getAlbum().getArtist().getName());

            Uri uri = Uri.parse(song.getAlbum().getImage().getUrl());

            holder.imageSong.setImageURI(uri);

            holder.songCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Log.i("click", "clikeado xd");
                        try {
                            Intent intent = new Intent(view.getContext(),ShowLyricsActivity.class);

                            intent.putExtra(SONG_ID,song.getId());
                            /*intent.putExtra(CATEGORY_ID,song.getCategoryId());
                            intent.putExtra(LAT_COMPLAINT,song.getLocation().getLat());
                            intent.putExtra(LONG_COMPLAINT,song.getLocation().getLng());*/

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
                        showPopupMenu(holder.moreImageButton,position,song);
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
        return songs.size();
    }

    /*private void showPopupMenu(View view,int position, SongCreate song) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_options_song_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position,song,view));
        popup.show();
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        private View view;
        private SongCreate song;
        MyMenuItemClickListener(int positon, SongCreate song, View view) {
            this.position = positon;
            this.song = song;
            this.view = view;

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {


                case R.id.deleteSong:
                    showDeleteMessage(view, song);
                    break;
                default:
            }
            return false;
        }
    }*/

    private void showDeleteMessage(View view, final SongCreate songCreate) {

        /*AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(view.getContext().getString(R.string.delete_confirmation_title));
        builder.setMessage(view.getContext().getString(R.string.delete_confirmation_description));

        String positiveText = view.getContext().getString(R.string.positive_option_alert_dialog);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // this make http request to create an songCreate
                        Call<ResponseBody> call = Api.instance().deleteSong(songCreate.getId(), Remember.getString("access_token",""));

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {

                                    System.out.println("Denuncia eliminada con éxito");


                                }
                                else {
                                    System.out.println("ERROR: "+response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                System.out.println("ERROR al eliminar: "+t.getMessage());
                            }
                        });


                    }
                });

        String negativeText = view.getContext().getString(R.string.negative_option_alert_dialog);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Eliminar denuncia cancelada");
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();*/
    }





}