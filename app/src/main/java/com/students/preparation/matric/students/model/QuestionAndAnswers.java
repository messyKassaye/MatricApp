package com.students.preparation.matric.students.model;

public class QuestionAndAnswers {
    private int questionNumber;
    private String question;
    private String answer;
    private String explanations;
    private Choices choices;
    private String type;
    private String base64Image;

    public QuestionAndAnswers() {
    }

    public QuestionAndAnswers(int questionNumber, String question, String answer, String explanations, Choices choices, String type, String base64Image) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answer = answer;
        this.explanations = explanations;
        this.choices = choices;
        this.type = type;
        this.base64Image = base64Image;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
