package com.torgaigym.torgai.torgaigym.admins;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.adapters.PagerAdapter;
import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseConsts;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtils;

public class AdminsActivity extends AppCompatActivity implements FirebaseUtils.ExerciseListener, TabLayout.OnTabSelectedListener, View.OnClickListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_1)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_2)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_3)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_4)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_5)));

        viewPager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void getResponse(Exercise exercise) {
        System.out.println("CHECK_READ### " + exercise.getName() + " / " + exercise.getDescription());
    }

    @Override
    public void onClick(View v) {
        //                FirebaseUtils firebaseUtils = new FirebaseUtils();
//                Exercise exercise = new Exercise("ExampleOne", "This is just example");
//                firebaseUtils.writeDayExercises(FirebaseConsts.dayOne, exercise);
//                exercise = new Exercise("ExampleTwo", "This is just example");
//                firebaseUtils.writeDayExercises(FirebaseConsts.dayTwo, exercise);
//                exercise = new Exercise("ExampleThree", "This is just example");
//                firebaseUtils.writeDayExercises(FirebaseConsts.dayThree, exercise);
//                exercise = new Exercise("ExampleFour", "This is just example");
//                firebaseUtils.writeDayExercises(FirebaseConsts.dayFour, exercise);
//                exercise = new Exercise("ExampleFive", "This is just example");
//                firebaseUtils.writeDayExercises(FirebaseConsts.dayFive, exercise);

        FirebaseUtils firebaseUtils = new FirebaseUtils();
        firebaseUtils.readDayExercises(FirebaseConsts.dayOne, AdminsActivity.this);

        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
