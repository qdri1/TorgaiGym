package com.torgaigym.torgai.torgaigym.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.torgaigym.torgai.torgaigym.compat.FragmentStatePagerAdapter;
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
    protected void handleGetItemInbalidated(View container, Fragment oldFragment, Fragment newFragment) {
        System.out.println("#########oldFragment: " + oldFragment);
        System.out.println("#########newFragment: " + newFragment);

        int position = getFragmentPosition(newFragment);


    }

    @Override
    protected int getFragmentPosition(Fragment fragment) {


        switch (fragment.getClass().getSimpleName()) {
            case "asd":

                break;
        }
        System.out.println("#########classes1: " + fragment.getClass().getSimpleName());
        System.out.println("#########classes2: " + DayFourFragment.TAG);

        return 0;
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
