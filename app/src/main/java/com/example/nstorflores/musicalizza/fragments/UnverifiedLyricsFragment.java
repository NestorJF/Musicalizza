package com.example.nstorflores.musicalizza.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nstorflores.musicalizza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnverifiedLyricsFragment extends Fragment {


    private AppBarLayout appBar;
    private TabLayout pestanas;

    public UnverifiedLyricsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_unverified_lyrics, container, false);

        return view;
    }

}
