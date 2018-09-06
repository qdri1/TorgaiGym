package com.torgaigym.torgai.torgaigym.presenter;

import android.support.v4.app.Fragment;

import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtilsModel;
import com.torgaigym.torgai.torgaigym.interfaces.DaysInterface;

import java.util.List;

public class DaysPresenter {

    private DaysInterface view;
    private final FirebaseUtilsModel model;

    public DaysPresenter(FirebaseUtilsModel model) {
        this.model = model;
    }

    public void attachView(DaysInterface view) {
        this.view = view;
    }

    public void dettachView() {
        view = null;
    }

    public void loadExercises(String dayId) {
        model.readDayExercises(dayId, new FirebaseUtilsModel.ExerciseListener() {
            @Override
            public void getResponse(List<Exercise> exercises) {
                view.updateList(exercises);
            }
        });
    }

}
