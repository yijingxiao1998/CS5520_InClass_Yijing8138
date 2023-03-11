package com.example.cs5520_inclass_yijing8138.InClass06;

/**
 * A class for storing all article information from JSON object.
 */
public class Article {
    private String title, author, publishedTime, description, image;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedTime='" + publishedTime + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Article() {
    }

    public Article(String title, String author, String publishedTime, String description, String image) {
        this.title = title;
        this.author = author;
        this.publishedTime = publishedTime;
        this.description = description;
        this.image = image;
    }
}
