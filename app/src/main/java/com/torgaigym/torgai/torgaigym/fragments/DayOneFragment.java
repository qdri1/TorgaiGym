package com.torgaigym.torgai.torgaigym.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.adapters.ExercisesListAdapter;
import com.torgaigym.torgai.torgaigym.classes.Exercise;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseConsts;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtilsModel;
import com.torgaigym.torgai.torgaigym.interfaces.DaysInterface;
import com.torgaigym.torgai.torgaigym.presenter.AdminsPresenter;
import com.torgaigym.torgai.torgaigym.presenter.DaysPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayOneFragment extends Fragment implements DaysInterface {

    public static final String TAG = DayOneFragment.class.getSimpleName();

    private DaysPresenter presenter;
    private ExpandableListView expandableListView;
    private ExercisesListAdapter adapter;
    private List<List<Exercise>> groups = new ArrayList<>();

    public static DayOneFragment newInstance() {
        Bundle args = new Bundle();
        DayOneFragment fragment = new DayOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_one, container, false);
        expandableListView = v.findViewById(R.id.exp_list_view);
        return v;
    }


    public int lastPositionOfList() {
        return groups.size();
    }

    @Override
    public void updateList(List<Exercise> exercises) {
        System.out.println("###########1");
        groups = new ArrayList<>();
        groups.add(exercises);
        if (!groups.isEmpty()) {
            adapter = new ExercisesListAdapter(getContext(), groups);
            expandableListView.setAdapter(adapter);
        }
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FirebaseUtilsModel model = new FirebaseUtilsModel();
            presenter = new DaysPresenter(model);
            presenter.attachView(this);
            presenter.loadExercises(FirebaseConsts.dayOne);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("ASD###onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("ASD###onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("ASD###onDestroy");
    }

}
