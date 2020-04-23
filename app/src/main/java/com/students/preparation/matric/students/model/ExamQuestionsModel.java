package com.students.preparation.matric.students.model;

public class ExamQuestionsModel {

    public String examAnswer;
    public String examAnswerExplanation;
    public String examChoice1;
    public String examChoice2;
    public String examChoice3;
    public String examChoice4;
    public String examId;
    public String examOptionalImage;
    public String examQuestion;
    public String examSubject;
    public String examYear;

    public ExamQuestionsModel(){}

    public ExamQuestionsModel(String examAnswer, String examAnswerExplanation, String examChoice1, String examChoice2, String examChoice3, String examChoice4, String examId, String examOptionalImage, String examQuestion, String examSubject, String examYear) {
        this.examAnswer = examAnswer;
        this.examAnswerExplanation = examAnswerExplanation;
        this.examChoice1 = examChoice1;
        this.examChoice2 = examChoice2;
        this.examChoice3 = examChoice3;
        this.examChoice4 = examChoice4;
        this.examId = examId;
        this.examOptionalImage = examOptionalImage;
        this.examQuestion = examQuestion;
        this.examSubject = examSubject;
        this.examYear = examYear;
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

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamOptionalImageUrl() {
        return examOptionalImage;
    }

    public void setExamOptionalImageUrl(String examOptionalImageUrl) {
        this.examOptionalImage = examOptionalImageUrl;
    }

    public String getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(String examQuestion) {
        this.examQuestion = examQuestion;
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
}
