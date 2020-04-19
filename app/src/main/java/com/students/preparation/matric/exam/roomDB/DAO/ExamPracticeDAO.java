package com.students.preparation.matric.exam.roomDB.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.students.preparation.matric.exam.roomDB.entity.ExamPractice;

@Dao
public interface ExamPracticeDAO {

    @Query("select * from examPractice where examName=:examName")
    public ExamPractice show(String examName);

    @Insert
    public void store(ExamPractice examPractice);

    @Update
    public void update(ExamPractice examPractice);
}
