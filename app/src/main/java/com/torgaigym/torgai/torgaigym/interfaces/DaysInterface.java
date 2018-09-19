package com.torgaigym.torgai.torgaigym.interfaces;

import com.torgaigym.torgai.torgaigym.classes.Exercise;

import java.util.List;

public interface DaysInterface {
    void updateList(List<Exercise> exercises);

    void updateCurrentItem(int position, Exercise exercise);

    void removeCurrentItem(int position);
}
