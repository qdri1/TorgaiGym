package com.torgaigym.torgai.torgaigym.admins;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.adapters.PagerAdapter;
import com.torgaigym.torgai.torgaigym.dialogs.GymDialogs;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtilsModel;
import com.torgaigym.torgai.torgaigym.presenter.AdminsPresenter;

public class AdminsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private ViewPager viewPager;
    private AdminsPresenter presenter;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_1)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_2)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_3)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_4)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_5)));

        viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        FirebaseUtilsModel model = new FirebaseUtilsModel();
        presenter = new AdminsPresenter(model);
        presenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        presenter.showDialogToAddExercise();
    }

    public void showDialog() {
        GymDialogs.addExercisesDialog(getApplicationContext(), getString(R.string.label_add_exercise), new GymDialogs.TextInputListener() {
            @Override
            public void onClick(String name, String desc) {
                presenter.saveExercise(viewPager.getCurrentItem(), adapter.getPositionOfLastAddedExercise(viewPager.getCurrentItem()), name, desc);
            }
        }).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettachView();
    }
}
