package com.students.preparation.matric.students.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.roomDB.DAO.ExamImageDAO;
import com.students.preparation.matric.students.roomDB.DAO.ExamPracticeDAO;
import com.students.preparation.matric.students.roomDB.DAO.MyNotesDAO;
import com.students.preparation.matric.students.roomDB.entity.ExamPractice;
import com.students.preparation.matric.students.roomDB.entity.ExamQuestionImage;
import com.students.preparation.matric.students.roomDB.entity.Notes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ExamQuestionImage.class,
        ExamPractice.class, Notes.class},version = 1,exportSchema = false)
public abstract class MatricAppDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THEARD = 4;
    private static volatile MatricAppDatabase INSTANCE;
    public static final ExecutorService dbExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THEARD);

    public static synchronized MatricAppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MatricAppDatabase.class, Constants.getDbName())
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract ExamImageDAO getExamDAO();

    public abstract ExamPracticeDAO getExamPracticeDAO();

    public abstract MyNotesDAO getMyNotesDAO();
}