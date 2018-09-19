package com.torgaigym.torgai.torgaigym.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.classes.Exercise;

import java.util.List;

public class ExercisesListAdapter extends BaseExpandableListAdapter {

    private List<List<Exercise>> mGroups;
    private Context context;
    private Listener listener;

    public ExercisesListAdapter(Context context, List<List<Exercise>> mGroups, Listener listener) {
        this.mGroups = mGroups;
        this.context = context;
        this.listener = listener;
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
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {

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
        textView.setText(context.getString(R.string.set_of_exercise) + " " + Integer.toString(groupPosition + 1));

        if (listener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onGroupItemClicked(groupPosition, isExpanded);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongGroupItemClicked(groupPosition);
                    return false;
                }
            });
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_list_item_child, null);
        }

        TextView text1View = convertView.findViewById(R.id.text1);
        TextView text2View = convertView.findViewById(R.id.text2);
        text1View.setText(mGroups.get(groupPosition).get(childPosition).getName());
        text2View.setText(mGroups.get(groupPosition).get(childPosition).getDescription());

        if (listener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChildItemClicked(groupPosition, mGroups.get(groupPosition).get(childPosition).getName(), mGroups.get(groupPosition).get(childPosition).getDescription());
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface Listener {
        void onGroupItemClicked(int position, boolean isExpanded);

        void onLongGroupItemClicked(int position);

        void onChildItemClicked(int position, String name, String desc);
    }

    public void updateChildByPosition(int position, Exercise exercise) {
        mGroups.get(position).set(0, exercise);
        notifyDataSetChanged();
    }

    public void removeItemByPosition(int position) {
        mGroups.remove(position);
        notifyDataSetChanged();
    }

}
