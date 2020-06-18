package com.example.demochatsms16.dialog;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.demochatsms16.R;

public class CommonDialog {

    public static void showConfirmValidate(Application activity, int id) {
        createAlertDialog(activity, activity.getResources().getString(id),
                activity.getResources().getString(R.string.app_name));
    }

    public static Dialog createAlertDialog(Application act, String message, String title) {
        if (!CommonActivity.isNullOrEmpty(message)) {
            if (message.contains("java.lang") || message.contains("xception")) {
                message = act.getString(R.string.errorProcess) + message.substring(0, 30);
            }
        }
        try {
           return new AlertDialog.Builder(act)
                    .setTitle(title)
                    .setMessage(message)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } catch (Exception e) {
         //   Log.d(TAG, "Error", e);
            return null;
        }
    }

}
