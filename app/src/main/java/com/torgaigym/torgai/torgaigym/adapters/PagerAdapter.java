package com.torgaigym.torgai.torgaigym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.torgaigym.torgai.torgaigym.fragments.DayFiveFragment;
import com.torgaigym.torgai.torgaigym.fragments.DayFourFragment;
import com.torgaigym.torgai.torgaigym.fragments.DayOneFragment;
import com.torgaigym.torgai.torgaigym.fragments.DayThreeFragment;
import com.torgaigym.torgai.torgaigym.fragments.DayTwoFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabs;

    public PagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DayOneFragment();
            case 1:
                return new DayTwoFragment();
            case 2:
                return new DayThreeFragment();
            case 3:
                return new DayFourFragment();
            case 4:
                return new DayFiveFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }

    public int getPositionOfLastAddedExercise(int currentDayPosition) {
        switch (currentDayPosition) {
            case 0:
                return new DayOneFragment().lastPositionOfList();
            case 1:
                return new DayTwoFragment().lastPositionOfList();
            case 2:
                return new DayThreeFragment().lastPositionOfList();
            case 3:
                return new DayFourFragment().lastPositionOfList();
            case 4:
                return new DayFiveFragment().lastPositionOfList();
            default:
                return 0;
        }
    }
}
