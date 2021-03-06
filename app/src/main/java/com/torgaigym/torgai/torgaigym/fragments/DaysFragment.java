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
    private boolean init = true;

    public static DaysFragment newInstance(int position, boolean isAdmin) {

        Bundle args = new Bundle();

        DaysFragment fragment = new DaysFragment();
        args.putInt(TorgaiGymConsts.POSITION, position);
        args.putBoolean(TorgaiGymConsts.IS_ADMIN, isAdmin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_days, container, false);
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
                adapter = new ExercisesListAdapter(getContext(), groups, getArguments().getBoolean(TorgaiGymConsts.IS_ADMIN) ? this : null);
                expandableListView.setAdapter(adapter);
            }
        }
    }


    public void addItemToList(Exercise exercise) {
        groups.add(new ArrayList<Exercise>());
        groups.get(groups.size() - 1).add(exercise);
        adapter = new ExercisesListAdapter(getContext(), groups, getArguments().getBoolean(TorgaiGymConsts.IS_ADMIN) ? this : null);
        expandableListView.setAdapter(adapter);
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
        } else {
            expandableListView.expandGroup(position);
        }
    }

    @Override
    public void onLongGroupItemClicked(final int position) {
        GymDialogs.deleteExercisesDialog(getContext(), getString(R.string.label_delete_exercise), new GymDialogs.OnClickListener() {
            @Override
            public void onClick() {
                presenter.deleteExercise(getArguments().getInt(TorgaiGymConsts.POSITION), position, groups.get(position).get(0).getExerciseId());
            }
        }).show();
    }

    @Override
    public void onChildItemClicked(final int position, String name, String desc) {
        GymDialogs.updateExercisesDialog(getContext(), getString(R.string.label_edit_exercise), name, desc, new GymDialogs.TextInputListener() {
            @Override
            public void onClick(String name, String desc) {
                presenter.updateExercise(getArguments().getInt(TorgaiGymConsts.POSITION), position, groups.get(position).get(0).getExerciseId(), name, desc);
            }
        }).show();
    }

    @Override
    public void updateCurrentItem(int position, Exercise exercise) {
        adapter.updateChildByPosition(position, exercise);
    }

    @Override
    public void removeCurrentItem(int position) {
        adapter.removeItemByPosition(position);
    }
}
