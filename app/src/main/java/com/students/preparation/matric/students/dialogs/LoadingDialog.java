package com.students.preparation.matric.students.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

import com.students.preparation.matric.students.R;

public class LoadingDialog {

    public static ProgressDialog loadingDialog(Context context, String message){
        final ProgressDialog dialog = new ProgressDialog(context, R.style.Theme_AppCompat_Light_Dialog);
        dialog.setIndeterminate(true);
        dialog.setMessage(message);
        return dialog;
    }
}
