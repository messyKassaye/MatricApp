package com.students.preparation.matric.exam.model;

public class QuestionAndAnswers {
    private int questionNumber;
    private String question;
    private String answer;
    private String explanations;
    private Choices choices;

    public QuestionAndAnswers() {
    }

    public QuestionAndAnswers(int questionNumber, String question, String answer, String explanations, Choices choices) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answer = answer;
        this.explanations = explanations;
        this.choices = choices;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public String getExplanations() {
        return explanations;
    }

    public void setExplanations(String explanations) {
        this.explanations = explanations;
    }

    public Choices getChoices() {
        return choices;
    }

    public void setChoices(Choices choices) {
        this.choices = choices;
    }
}
