package com.example.nstorflores.musicalizza.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nstorflores.musicalizza.R;

import java.util.List;

/**
 * Created by Néstor Flores on 8/5/2018.
 */

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.ViewHolder> {

    private String[]  lyrics;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView lyrics;
        public CardView songCardView;


        public ViewHolder(View view) {
            super(view);
            lyrics = view.findViewById(R.id.pagraph_lyric);
            songCardView = view.findViewById(R.id.lyrics_card_view);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LyricsAdapter(String[]  lyrics) {
        this.lyrics = lyrics;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LyricsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lyrics_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        LyricsAdapter.ViewHolder viewHolder = new LyricsAdapter.ViewHolder(view);

        return viewHolder;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final LyricsAdapter.ViewHolder holder, final int position) {
        final String lyric = lyrics[position] ;

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try{

            Log.i("LETRAS: ",lyric);
            holder.lyrics.setText(lyric);

        }catch (Exception e){
            Log.e("ERROR: ",e.getMessage());
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lyrics.length;
    }


}
