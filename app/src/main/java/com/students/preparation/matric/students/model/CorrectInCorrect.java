package com.students.preparation.matric.students.model;

public class CorrectInCorrect {
    private int questionNumber;
    private String answer;

    public CorrectInCorrect(int questionNumber, String answer) {
        this.questionNumber = questionNumber;
        this.answer = answer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
