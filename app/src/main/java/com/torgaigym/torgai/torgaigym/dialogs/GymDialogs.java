package com.torgaigym.torgai.torgaigym.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.torgaigym.torgai.torgaigym.R;

public class GymDialogs {

    public static AlertDialog tabataTimerExercise(final Context context, String title, final TimerInputListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_tabata_timer_dialog_view, null);
        TextView titleView = view.findViewById(R.id.title);
        titleView.setText(title);
        final EditText t1View = view.findViewById(R.id.t_1);
        final EditText t2View = view.findViewById(R.id.t_2);
        final EditText tabataRoundsView = view.findViewById(R.id.tabata_rounds);
        dialogBuilder.setView(view);
        dialogBuilder.setNegativeButton(R.string.action_no, null);
        dialogBuilder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(t1View.getText())) {
                    t1View.setError(context.getString(R.string.label_error_empty_text));
                } else if (TextUtils.isEmpty(t2View.getText())) {
                    t2View.setError(context.getString(R.string.label_error_empty_text));
                } else if (TextUtils.isEmpty(tabataRoundsView.getText())) {
                    tabataRoundsView.setError(context.getString(R.string.label_error_empty_text));
                } else {
                    listener.onClick(Integer.parseInt(t1View.getText().toString()), Integer.parseInt(t2View.getText().toString()), Integer.parseInt(tabataRoundsView.getText().toString()));
                }
            }
        });
        return dialogBuilder.create();
    }

    public static AlertDialog addExercisesDialog(final Context context, String title, final TextInputListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_exercises_dialog_view, null);
        TextView titleView = view.findViewById(R.id.title);
        titleView.setText(title);
        final EditText editNameView = view.findViewById(R.id.name);
        final EditText editDescView = view.findViewById(R.id.desc);
        dialogBuilder.setView(view);
        dialogBuilder.setNegativeButton(R.string.action_no, null);
        dialogBuilder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(editNameView.getText())) {
                    editNameView.setError(context.getString(R.string.label_error_empty_text));
                } else if (TextUtils.isEmpty(editDescView.getText())) {
                    editDescView.setError(context.getString(R.string.label_error_empty_text));
                } else {
                    listener.onClick(editNameView.getText().toString(), editDescView.getText().toString());
                }
            }
        });
        return dialogBuilder.create();
    }

    public static AlertDialog updateExercisesDialog(final Context context, String title, String name, String desc, final TextInputListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_exercises_dialog_view, null);
        TextView titleView = view.findViewById(R.id.title);
        titleView.setText(title);
        final EditText editNameView = view.findViewById(R.id.name);
        editNameView.setText(name);
        editNameView.setSelection(name.length());
        final EditText editDescView = view.findViewById(R.id.desc);
        editDescView.setText(desc);
        dialogBuilder.setView(view);
        dialogBuilder.setNegativeButton(R.string.action_no, null);
        dialogBuilder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(editNameView.getText())) {
                    editNameView.setError(context.getString(R.string.label_error_empty_text));
                } else if (TextUtils.isEmpty(editDescView.getText())) {
                    editDescView.setError(context.getString(R.string.label_error_empty_text));
                } else {
                    listener.onClick(editNameView.getText().toString(), editDescView.getText().toString());
                }
            }
        });
        return dialogBuilder.create();
    }

    public static AlertDialog deleteExercisesDialog(final Context context, String title, final OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(title);
        dialogBuilder.setNegativeButton(R.string.action_no, null);
        dialogBuilder.setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick();
            }
        });
        return dialogBuilder.create();
    }

    public static AlertDialog inputDialog(final Context context, String title, final SimpleTextInputListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.input_dialog_view, null);
        TextView titleView = view.findViewById(R.id.title);
        titleView.setText(title);
        final EditText editView = view.findViewById(R.id.value);
        dialogBuilder.setView(view);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setNegativeButton(R.string.action_cancel, null);
        dialogBuilder.setPositiveButton(R.string.action_continue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(editView.getText())) {
                    editView.setError(context.getString(R.string.label_error_empty_text));
                } else {
                    listener.onClick(editView.getText().toString());
                }
            }
        });
        return dialogBuilder.create();
    }

    public interface TextInputListener {
        void onClick(String name, String desc);
    }

    public interface SimpleTextInputListener {
        void onClick(String value);
    }

    public interface OnClickListener {
        void onClick();
    }

    public interface TimerInputListener {
        void onClick(int t1, int t2, int rounds);
    }

}
