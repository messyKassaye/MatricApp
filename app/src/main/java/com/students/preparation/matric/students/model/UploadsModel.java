package com.students.preparation.matric.students.model;

public class UploadsModel {
    public String title;
    public String url;
    public String stream;
    public String grade;
    public String type;
    public String subject;
    public String timestamp;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UploadsModel() {
    }

    public UploadsModel(String title, String url, String stream,String grade, String type, String subject , String timestamp) {
        this.title = title;
        this.url = url;
        this.stream = stream;
        this.grade = grade;
        this.type = type;
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
}
