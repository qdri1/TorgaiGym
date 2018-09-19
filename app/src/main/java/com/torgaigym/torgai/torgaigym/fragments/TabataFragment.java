package com.torgaigym.torgai.torgaigym.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.torgaigym.torgai.torgaigym.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabataFragment extends Fragment {

    public static TabataFragment newInstance() {

        Bundle args = new Bundle();

        TabataFragment fragment = new TabataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabata, container, false);
    }

}
