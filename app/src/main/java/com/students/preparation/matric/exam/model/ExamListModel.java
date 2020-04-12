package com.students.preparation.matric.exam.model;

public class ExamListModel {

    public String examName;
    public ExamListModel(){}

    public ExamListModel(String examName){
        this.examName = examName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
