package com.students.preparation.matric.exam.model;

public class Questions {
    private int questionNumber;
    private String questions;
    private String choice1,choice2,choice3,choice4;
    private String answer;
    private String explanations;

    public Questions() {
    }

    public Questions(int questionNumber, String questions, String choice1, String choice2, String choice3, String choice4, String answer, String explanations) {
        this.questionNumber = questionNumber;
        this.questions = questions;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.explanations = explanations;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplanations() {
        return explanations;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setExplanations(String explanations) {
        this.explanations = explanations;
    }
}
