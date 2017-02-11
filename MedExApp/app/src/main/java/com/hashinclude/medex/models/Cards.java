package com.hashinclude.medex.models;

/**
 * Created by pankaj on 11/2/17.
 */

public class Cards {
    private String title;
    private int thumbnail;
    private String content;

    public Cards() {
    }

    public Cards(String title, int thumbnail, String content) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;

    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
