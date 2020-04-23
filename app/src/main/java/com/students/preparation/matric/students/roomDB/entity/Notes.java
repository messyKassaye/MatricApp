package com.students.preparation.matric.students.roomDB.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mynotes")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "subject")
    private String subject;
    @ColumnInfo(name = "content")
    private String content;

    public Notes() {
    }

    @Ignore
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
