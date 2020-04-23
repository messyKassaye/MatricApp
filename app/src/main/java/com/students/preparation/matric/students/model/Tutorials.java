package com.students.preparation.matric.students.model;

public class Tutorials {
    private String title;
    private String stream;
    private String grade;
    private String subject;
    private String youtubeLink;


    public Tutorials() {
    }

    public Tutorials(String title, String stream, String grade, String subject, String youtubeLink) {
        this.title = title;
        this.stream = stream;
        this.grade = grade;
        this.subject = subject;
        this.youtubeLink = youtubeLink;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
