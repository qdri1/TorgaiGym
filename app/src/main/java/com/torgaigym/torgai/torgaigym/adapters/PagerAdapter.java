package com.torgaigym.torgai.torgaigym.adapters;

import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.torgaigym.torgai.torgaigym.fragments.DaysFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabs;
    private Fragment fragments[];

    public PagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
        fragments = new Fragment[tabs];
    }

    @Override
    public Fragment getItem(@IntRange(from = 0, to = 4) int position) {
        if (fragments.length < 5) {
            fragments[position] = DaysFragment.newInstance(position);
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tabs;
    }

    public int getPositionOfLastAddedExercise(int currentDayPosition) {
        return ((DaysFragment) fragments[currentDayPosition]).getLastPositionOfList();
    }
}
