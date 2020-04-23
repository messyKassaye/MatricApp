package com.students.preparation.matric.students.roomDB.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "examsImage")
public class ExamQuestionImage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "questionName")
    private String questionName;

    @ColumnInfo(name = "filePath")
    private String filePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
