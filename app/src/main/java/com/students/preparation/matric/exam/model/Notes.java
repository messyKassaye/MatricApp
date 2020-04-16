package com.students.preparation.matric.exam.model;

public class Notes {
    private String phone;
    private String title;
    private String subject;
    private String content;

    public Notes() {
    }

    public Notes(String phone, String title, String subject, String content) {
        this.phone = phone;
        this.title = title;
        this.subject = subject;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
