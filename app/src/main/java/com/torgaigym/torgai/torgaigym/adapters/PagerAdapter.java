package com.torgaigym.torgai.torgaigym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.torgaigym.torgai.torgaigym.fragments.DaysFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabs;
    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.size() < 5) {
            fragments.add(DaysFragment.newInstance(position));
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabs;
    }

    public int getPositionOfLastAddedExercise(int currentDayPosition) {
        return ((DaysFragment) fragments.get(currentDayPosition)).getLastPositionOfList();
    }
}
