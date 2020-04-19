package com.students.preparation.matric.exam.model;

public class Exams {
    private String examType;
    private String examSubject;
    private int examYear;
    private int totalQuestionNumber;
    private String fileName;
    private String jsonDownloadUrl;
    private String examTime;
    private String exaplanantions;

    public Exams() {
    }

    public Exams(String examType, String examSubject, int examYear, int totalQuestionNumber, String fileName, String jsonDownloadUrl, String examTime, String exaplanantions) {
        this.examType = examType;
        this.examSubject = examSubject;
        this.examYear = examYear;
        this.totalQuestionNumber = totalQuestionNumber;
        this.fileName = fileName;
        this.jsonDownloadUrl = jsonDownloadUrl;
        this.examTime = examTime;
        this.exaplanantions = exaplanantions;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(String examSubject) {
        this.examSubject = examSubject;
    }

    public int getExamYear() {
        return examYear;
    }

    public void setExamYear(int examYear) {
        this.examYear = examYear;
    }

    public int getTotalQuestionNumber() {
        return totalQuestionNumber;
    }

    public void setTotalQuestionNumber(int totalQuestionNumber) {
        this.totalQuestionNumber = totalQuestionNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJsonDownloadUrl() {
        return jsonDownloadUrl;
    }

    public void setJsonDownloadUrl(String jsonDownloadUrl) {
        this.jsonDownloadUrl = jsonDownloadUrl;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExaplanantions() {
        return exaplanantions;
    }

    public void setExaplanantions(String exaplanantions) {
        this.exaplanantions = exaplanantions;
    }
}
