package com.example.nstorflores.musicalizza.Adapters;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nstorflores.musicalizza.R;
import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Néstor Flores on 3/5/2018.
 */

public class AlbumsAdapter  extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {


    private List<Album> albums;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public CardView songCardView;
        public SimpleDraweeView imageAlbum;
        public TextView artistName;
        public ImageView moreImageButton;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            songCardView = view.findViewById(R.id.card_view);
            imageAlbum = view.findViewById(R.id.song_image);
            artistName = view.findViewById(R.id.artist_name);
            moreImageButton = view.findViewById(R.id.overflow);
        }

    }


    public AlbumsAdapter(List<Album> albums) {
        this.albums = albums;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AlbumsAdapter.ViewHolder viewHolder = new AlbumsAdapter.ViewHolder(view);

        return viewHolder;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AlbumsAdapter.ViewHolder holder, final int position) {
        final Album album = albums.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try{

            holder.title.setText(album.getName());
            // Log.i("Genre", song.getGenre().getName());
            holder.artistName.setText(album.getArtist().getName());

            Uri uri = Uri.parse(album.getImage().getUrl());

            holder.imageAlbum.setImageURI(uri);

            /*holder.song.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        try {
                            Intent intent = new Intent(view.getContext(),.class);

                            intent.putExtra(COMPLAINT_ID,song.getId());
                            intent.putExtra(CATEGORY_ID,song.getCategoryId());
                            intent.putExtra(LAT_COMPLAINT,song.getLocation().getLat());
                            intent.putExtra(LONG_COMPLAINT,song.getLocation().getLng());

                            view.getContext().startActivity(intent);

                        }catch (Exception e){
                            Log.e("ERROR: ",e.getMessage());
                        }

                    }
                });


            holder.moreImageButton.setOnClickListener(new View.OnClickListener() {
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
            return albums.size();
        }
}
