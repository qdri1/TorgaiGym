package com.torgaigym.torgai.torgaigym.presenter;

import com.torgaigym.torgai.torgaigym.admins.AdminsActivity;
import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseConsts;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtilsModel;

public class AdminsPresenter {

    private AdminsActivity view;
    private final FirebaseUtilsModel model;

    public AdminsPresenter(FirebaseUtilsModel model) {
        this.model = model;
    }

    public void attachView(AdminsActivity view) {
        this.view = view;
    }

    public void dettachView() {
        view = null;
    }

    public void showDialogToAddExercise() {
        view.showDialog();
    }

    public void saveExercise(int currentItemPosition, int position, String name, String desc) {
        String dayConst = FirebaseConsts.getDayConstByDayPosition(currentItemPosition);
        Exercise exercise = new Exercise(name, desc);
        model.writeDayExercises(dayConst, FirebaseConsts.exerciseN + position, exercise);
    }

}
