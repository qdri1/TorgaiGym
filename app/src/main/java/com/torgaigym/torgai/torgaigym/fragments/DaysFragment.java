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
import com.torgaigym.torgai.torgaigym.consts.TorgaiGymConsts;
import com.torgaigym.torgai.torgaigym.dialogs.GymDialogs;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseConsts;
import com.torgaigym.torgai.torgaigym.firebase.FirebaseUtilsModel;
import com.torgaigym.torgai.torgaigym.interfaces.DaysInterface;
import com.torgaigym.torgai.torgaigym.presenter.DaysPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaysFragment extends Fragment implements DaysInterface, ExercisesListAdapter.Listener {

    public static final String TAG = DaysFragment.class.getSimpleName();

    private DaysPresenter presenter;
    private ExercisesListAdapter adapter;
    private ExpandableListView expandableListView;
    private List<List<Exercise>> groups;
    private boolean[] groupsIsExpaned;
    private boolean init = true;

    public static DaysFragment newInstance(int position) {

        Bundle args = new Bundle();

        DaysFragment fragment = new DaysFragment();
        args.putInt(TorgaiGymConsts.POSITION, position);
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

    public int getLastPositionOfList() {
        return groups.size();
    }

    @Override
    public void updateList(List<Exercise> exercises) {
        if (init) {
            init = false;
            groups = new ArrayList<>();

            if (!exercises.isEmpty()) {
                for (int i = 0; i < exercises.size(); i++) {
                    groups.add(new ArrayList<Exercise>());
                    groups.get(i).add(exercises.get(i));
                }
            }

            if (!groups.isEmpty()) {
                if (groupsIsExpaned == null) {
                    groupsIsExpaned = new boolean[groups.size()];
                }
                adapter = new ExercisesListAdapter(getContext(), groups, this);
                expandableListView.setAdapter(adapter);
                for (int i = 0; i < groupsIsExpaned.length; i++) {
                    if (groupsIsExpaned[i]) {
                        expandableListView.expandGroup(i);
                    } else {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FirebaseUtilsModel model = new FirebaseUtilsModel();
            presenter = new DaysPresenter(model);
            presenter.attachView(this);
            presenter.loadExercises(FirebaseConsts.getDayConstByDayPosition(getArguments().getInt(TorgaiGymConsts.POSITION)));
        }
    }

    @Override
    public void onGroupItemClicked(int position, boolean isExpanded) {
        if (isExpanded) {
            expandableListView.collapseGroup(position);
            groupsIsExpaned[position] = false;
        } else {
            expandableListView.expandGroup(position);
            groupsIsExpaned[position] = true;
        }
    }

    @Override
    public void onChildItemClicked(final int position, String name, String desc) {
        GymDialogs.updateExercisesDialog(getContext(), getString(R.string.label_edit_exercise), name, desc, new GymDialogs.TextInputListener() {
            @Override
            public void onClick(String name, String desc) {
                presenter.updateExercise(getArguments().getInt(TorgaiGymConsts.POSITION), position, name, desc);
            }
        }).show();
    }

    @Override
    public void updateCurrentItem(int position, String name, String desc) {
        adapter.updateChildByPosition(position, new Exercise(name, desc));
    }
}
