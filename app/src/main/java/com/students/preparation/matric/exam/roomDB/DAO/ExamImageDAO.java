package com.students.preparation.matric.exam.roomDB.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.students.preparation.matric.exam.roomDB.entity.ExamQuestionImage;

@Dao
public interface ExamImageDAO {

    @Query("select * from examsImage where filePath=:filePaths")
    public ExamQuestionImage show(String filePaths);

    @Insert
    public void store(ExamQuestionImage examQuestionImage);

}
