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

import java.util.List;

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
    public void getResponse(List<Exercise> exercises) {

    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void writeDataToFirebase() {
        FirebaseUtils firebaseUtils = new FirebaseUtils();
        Exercise exercise = new Exercise("ExampleOne", "This is just example1");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayOne, FirebaseConsts.exerciseN + "1", exercise);
        exercise = new Exercise("ExampleOneTwo", "This is just example2");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayOne, FirebaseConsts.exerciseN + "2", exercise);
        exercise = new Exercise("ExampleOneThree", "This is just example3");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayOne, FirebaseConsts.exerciseN + "3", exercise);
        exercise = new Exercise("ExampleTwo", "This is just example");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayTwo, FirebaseConsts.exerciseN + "1", exercise);
        exercise = new Exercise("ExampleThree", "This is just example");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayThree, FirebaseConsts.exerciseN + "1", exercise);
        exercise = new Exercise("ExampleFour", "This is just example");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayFour, FirebaseConsts.exerciseN + "1", exercise);
        exercise = new Exercise("ExampleFive", "This is just example");
        firebaseUtils.writeDayExercises(FirebaseConsts.dayFive, FirebaseConsts.exerciseN + "1", exercise);
    }

    private void readDataFromFirebase() {
        FirebaseUtils firebaseUtils = new FirebaseUtils();
        firebaseUtils.readDayExercises(FirebaseConsts.dayOne, AdminsActivity.this);
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
