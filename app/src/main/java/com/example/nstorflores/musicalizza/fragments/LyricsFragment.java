package com.example.nstorflores.musicalizza.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nstorflores.musicalizza.Adapters.ViewPagerTabsAdapter;
import com.example.nstorflores.musicalizza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LyricsFragment extends Fragment {


    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public LyricsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lyrics, container, false);
        if (savedInstanceState == null) {
            insertTabs(container);

            // Setear adaptador al viewpager.
            viewPager = view.findViewById(R.id.pager);
            initTabsOnViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;
    }

    private void insertTabs(ViewGroup container) {
        View padre = (View) container.getParent().getParent();
        appBar = padre.findViewById(R.id.appbar);
        tabLayout = new TabLayout(getActivity());
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(tabLayout);
    }


    private void initTabsOnViewPager(ViewPager viewPager) {
        ViewPagerTabsAdapter adapter = new ViewPagerTabsAdapter(getFragmentManager());
        adapter.addFragment(new HomeFragment(), getString(R.string.title_tab_home_fragment));
        adapter.addFragment(new UnverifiedLyricsFragment(), getString(R.string.title_tab_unverified_lyrics_fragment));
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(appBar != null)
        {
            appBar.removeView(tabLayout);
        }
    }

}


