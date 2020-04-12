package com.students.preparation.matric.exam.model;

public class EntranceExamModel {

    public String examId;
    public String examSubject;
    public String examYear;
    public String examQuestion;
    public String examChoice1;
    public String examChoice2;
    public String examChoice3;
    public String examChoice4;
    public String examOptionalImage;
    public String examAnswer;
    public String examAnswerExplanation;

    public EntranceExamModel() {
    }

    public EntranceExamModel(String examId, String examSubject, String examYear, String examQuestion, String examChoice1, String examChoice2, String examChoice3, String examChoice4, String examOptionalImage, String examAnswer, String examAnswerExplanation) {

        this.examId = examId;
        this.examSubject = examSubject;
        this.examYear = examYear;
        this.examQuestion = examQuestion;
        this.examChoice1 = examChoice1;
        this.examChoice2 = examChoice2;
        this.examChoice3 = examChoice3;
        this.examChoice4 = examChoice4;
        this.examOptionalImage = examOptionalImage;
        this.examAnswer = examAnswer;
        this.examAnswerExplanation = examAnswerExplanation;
    }


    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(String examSubject) {
        this.examSubject = examSubject;
    }

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public String getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(String examQuestion) {
        this.examQuestion = examQuestion;
    }

    public String getExamChoice1() {
        return examChoice1;
    }

    public void setExamChoice1(String examChoice1) {
        this.examChoice1 = examChoice1;
    }

    public String getExamChoice2() {
        return examChoice2;
    }

    public void setExamChoice2(String examChoice2) {
        this.examChoice2 = examChoice2;
    }

    public String getExamChoice3() {
        return examChoice3;
    }

    public void setExamChoice3(String examChoice3) {
        this.examChoice3 = examChoice3;
    }

    public String getExamChoice4() {
        return examChoice4;
    }

    public void setExamChoice4(String examChoice4) {
        this.examChoice4 = examChoice4;
    }

    public String getExamOptionalImage() {
        return examOptionalImage;
    }

    public void setExamOptionalImage(String examOptionalImage) {
        this.examOptionalImage = examOptionalImage;
    }

    public String getExamAnswer() {
        return examAnswer;
    }

    public void setExamAnswer(String examAnswer) {
        this.examAnswer = examAnswer;
    }

    public String getExamAnswerExplanation() {
        return examAnswerExplanation;
    }

    public void setExamAnswerExplanation(String examAnswerExplanation) {
        this.examAnswerExplanation = examAnswerExplanation;
    }
}
