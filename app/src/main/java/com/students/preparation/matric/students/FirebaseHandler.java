package com.students.preparation.matric.students;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //myRef.keepSynced(true);
    }
}