package com.students.preparation.matric.students.model;

public class Books {
    private String title;
    private String stream;
    private String grades;
    private String bookType;
    private String subject;
    private String downloadUrl;

    public Books() {
    }

    public Books(String title, String stream, String grades, String bookType, String subject, String downloadUrl) {
        this.title = title;
        this.stream = stream;
        this.grades = grades;
        this.bookType = bookType;
        this.subject = subject;
        this.downloadUrl = downloadUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
