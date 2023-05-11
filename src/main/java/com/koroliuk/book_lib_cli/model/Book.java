package com.koroliuk.book_lib_cli.model;

public class Book {
    int id;
    String title;
    int categoryId;

    public Book(Integer id, String title, int categoryId) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
