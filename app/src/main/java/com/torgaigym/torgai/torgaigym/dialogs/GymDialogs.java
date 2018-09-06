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

    public interface TextInputListener {
        void onClick(String name, String desc);
    }

}
