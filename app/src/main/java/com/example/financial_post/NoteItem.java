package com.example.financial_post;

public class NoteItem {
    private String title;
    private String content;
    private String date;

    public NoteItem() {
        super();
        title = "";
        content = "";
        date = "";
    }

    public NoteItem(String title,String content,String date) {
        super();
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
