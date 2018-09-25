package com.torgaigym.torgai.torgaigym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.fragments.DaysFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabs;
    private Fragment fragments[];
    private boolean isAdmin;

    public PagerAdapter(FragmentManager fm, int tabs, boolean isAdmin) {
        super(fm);
        this.tabs = tabs;
        this.isAdmin = isAdmin;
        fragments = new Fragment[tabs];
    }

    @Override
    public Fragment getItem(int position) {
        fragments[position] = DaysFragment.newInstance(position, isAdmin);
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tabs;
    }

    public int getPositionOfLastAddedExercise(int currentDayPosition) {
        return ((DaysFragment) fragments[currentDayPosition]).getLastPositionOfList();
    }

    public void addItemToDayPosition(int currentDayPosition, Exercise exercise) {
        ((DaysFragment) fragments[currentDayPosition]).addItemToList(exercise);
    }
}
