package com.torgaigym.torgai.torgaigym.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.adapters.PagerAdapter;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;
    private PagerAdapter adapter;

    public static PlanFragment newInstance() {

        Bundle args = new Bundle();

        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        initView(v);
        return v;
    }

    private void initView(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_1_shrt)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_2_shrt)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_3_shrt)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_4_shrt)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_5_shrt)));

        viewPager = view.findViewById(R.id.pager);
        if (hasInternetConn()) {
            view.findViewById(R.id.internet_connection).setVisibility(View.GONE);
            adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), false);
            viewPager.setAdapter(adapter);
        } else {
            view.findViewById(R.id.internet_connection).setVisibility(View.VISIBLE);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(this);
        autoSelectToday();
    }

    private boolean hasInternetConn() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void autoSelectToday() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day >= 2 && day <= 6) {
            viewPager.setCurrentItem(day - 2);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
