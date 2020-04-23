package com.students.preparation.matric.students.model;

public class AfterFinishModel {
    private int questionNumber;
    private String asnwer;


    public AfterFinishModel() {
    }

    public AfterFinishModel(int questionNumber, String asnwer) {
        this.questionNumber = questionNumber;
        this.asnwer = asnwer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getAsnwer() {
        return asnwer;
    }

    public void setAsnwer(String asnwer) {
        this.asnwer = asnwer;
    }
}
