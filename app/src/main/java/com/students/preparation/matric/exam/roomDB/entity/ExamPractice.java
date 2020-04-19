package com.students.preparation.matric.exam.roomDB.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "examPractice")
public class ExamPractice {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "examName")
    private String examName;

    @ColumnInfo(name = "lastPracticedData")
    private String lastPracticedDate;

    public ExamPractice(String examName, String lastPracticedDate) {
        this.examName = examName;
        this.lastPracticedDate = lastPracticedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getLastPracticedDate() {
        return lastPracticedDate;
    }

    public void setLastPracticedDate(String lastPracticedDate) {
        this.lastPracticedDate = lastPracticedDate;
    }
}
