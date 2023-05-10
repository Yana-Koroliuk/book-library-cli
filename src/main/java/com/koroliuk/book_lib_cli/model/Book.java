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

    public int getCategory_id() {
        return categoryId;
    }

    public void setCategory_id(int category_id) {
        this.categoryId = category_id;
    }
}
