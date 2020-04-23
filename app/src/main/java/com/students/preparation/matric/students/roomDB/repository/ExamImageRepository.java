package com.students.preparation.matric.students.roomDB.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.students.preparation.matric.students.roomDB.DAO.ExamImageDAO;
import com.students.preparation.matric.students.roomDB.MatricAppDatabase;
import com.students.preparation.matric.students.roomDB.entity.ExamQuestionImage;

import java.util.List;

public class ExamImageRepository {
    private ExamImageDAO advertDAO;
    private LiveData<List<ExamQuestionImage>> advertRooms;
    public ExamImageRepository(Application application){
        MatricAppDatabase roomDatabase = MatricAppDatabase.getDatabase(application);
        advertDAO = roomDatabase.getExamDAO();

    }


    public LiveData<List<ExamQuestionImage>> show(String id){
        return advertDAO.show(id);
    }
}
