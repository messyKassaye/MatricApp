package com.students.preparation.matric.exam.model;

public class TeachersModel {
    public String title;
    public String url;
    public String stream;
    public String grade;
    public String subject;
    public String timestamp;
    public String school;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public TeachersModel() {
    }

    public TeachersModel(String title, String url, String stream, String grade, String school, String subject , String timestamp) {
        this.title = title;
        this.url = url;
        this.stream = stream;
        this.grade = grade;
        this.school = school;
        this.subject = subject;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
