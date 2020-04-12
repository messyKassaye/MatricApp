package com.students.preparation.matric.exam.model;

public class NoteTipModel {
    public String title;
    public String stream;
    public String grade;
    public String type;
    public String subject;
    public String content;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public NoteTipModel() {
    }

    public NoteTipModel(String title, String stream, String grade, String type, String subject , String content) {
        this.title = title;
        this.stream = stream;
        this.grade = grade;
        this.type = type;
        this.subject = subject;
        this.content = content;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
