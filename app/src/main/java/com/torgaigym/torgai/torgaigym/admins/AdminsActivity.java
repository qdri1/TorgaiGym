package com.torgaigym.torgai.torgaigym.admins;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseConsts;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtils;

public class AdminsActivity extends AppCompatActivity implements FirebaseUtils.ExerciseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void getResponse(Exercise exercise) {
        System.out.println("CHECK_READ### " + exercise.getName() + " / " + exercise.getDescription());
    }
}
