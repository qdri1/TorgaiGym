package com.torgaigym.torgai.torgaigym.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.torgaigym.torgai.torgaigym.classes.Exercise;

public class FirebaseUtils {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public FirebaseUtils() {

    }

    public void writeDayExercises(String dayId, Exercise exercise) {
        mDatabase.child(FirebaseConsts.dayExerciseId).child(dayId).setValue(exercise);
    }

    public void readDayExercises(String dayId, final ExerciseListener listener) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Exercise exercise = dataSnapshot.getValue(Exercise.class);
                listener.getResponse(exercise);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.child(FirebaseConsts.dayExerciseId).child(dayId).addValueEventListener(valueEventListener);
    }

    public interface ExerciseListener {
        void getResponse(Exercise exercise);
    }

}
