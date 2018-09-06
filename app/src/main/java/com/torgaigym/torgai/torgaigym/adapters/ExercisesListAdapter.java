package com.torgaigym.torgai.torgaigym.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.classes.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExercisesListAdapter extends BaseExpandableListAdapter {

    private List<List<Exercise>> mGroups;
    private Context context;

    public ExercisesListAdapter(Context context, List<List<Exercise>> mGroups) {
        this.mGroups = mGroups;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_list_item_group, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textView = convertView.findViewById(R.id.textGroup);
        textView.setText("Group " + Integer.toString(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_list_item_child, null);
        }

        TextView text1View = convertView.findViewById(R.id.text1);
        TextView text2View = convertView.findViewById(R.id.text2);
        text1View.setText(mGroups.get(groupPosition).get(childPosition).getName());
        text2View.setText(mGroups.get(groupPosition).get(childPosition).getDescription());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
