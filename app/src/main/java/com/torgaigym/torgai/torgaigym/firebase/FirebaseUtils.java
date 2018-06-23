package com.torgaigym.torgai.torgaigym.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.torgaigym.torgai.torgaigym.classes.Exercise;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtils {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public FirebaseUtils() {

    }

    public void writeDayExercises(String dayId, String exerciseId, Exercise exercise) {
        mDatabase.child(FirebaseConsts.dayExerciseId).child(dayId).child(exerciseId).setValue(exercise);
    }

    public void readDayExercises(String dayId, final ExerciseListener listener) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Exercise> exercises = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    exercises.add(child.getValue(Exercise.class));
                }
                listener.getResponse(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.child(FirebaseConsts.dayExerciseId).child(dayId).addValueEventListener(valueEventListener);
    }

    public interface ExerciseListener {
        void getResponse(List<Exercise> exercises);
    }

}
