package com.torgaigym.torgai.torgaigym.presenter;

import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseConsts;
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

    public void updateExercise(int currentItemPosition, int position, String exerciseId, String name, String desc) {
        String dayConst = FirebaseConsts.getDayConstByDayPosition(currentItemPosition);
        Exercise exercise = new Exercise(exerciseId, name, desc);
        view.updateCurrentItem(position, exercise);
        model.writeDayExercises(dayConst, exerciseId, exercise);
    }

    public void deleteExercise(int currentItemPosition, int position, String exerciseId) {
        view.removeCurrentItem(position);
        String dayConst = FirebaseConsts.getDayConstByDayPosition(currentItemPosition);
        model.deleteDayExercises(dayConst, exerciseId);
    }
}
