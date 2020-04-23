package com.students.preparation.matric.students.roomDB.services;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.roomDB.MatricAppDatabase;
import com.students.preparation.matric.students.roomDB.entity.Notes;

import java.util.List;

public class NotesService {

    private MatricAppDatabase db ;

    public NotesService(Context context){
        db = Room.databaseBuilder(context,
                MatricAppDatabase.class, Constants.getDbName()).build();
    }

    public List<Notes> index(){
        try {
            return new GetUsersAsyncTask().execute().get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private class GetUsersAsyncTask extends AsyncTask<Void, Void,List<Notes>>
    {
        @Override
        protected List<Notes> doInBackground(Void... url) {
            return db.getMyNotesDAO().index();
        }
    }

}
