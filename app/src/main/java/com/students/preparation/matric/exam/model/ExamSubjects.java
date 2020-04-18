package com.students.preparation.matric.exam.model;

public class ExamSubjects {
    private int subjectImage;
    private String subject;

    public ExamSubjects() {
    }

    public ExamSubjects(int subjectImage, String subject) {
        this.subjectImage = subjectImage;
        this.subject = subject;
    }

    public int getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(int subjectImage) {
        this.subjectImage = subjectImage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
