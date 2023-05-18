package com.koroliuk.book_lib_cli.model;

public class Book {
    int id;
    String title;
    int categoryId;
    int exemplars;

    public Book(Integer id, String title, int categoryId, int exemplars) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.exemplars = exemplars;
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

    public int getExemplars() {
        return exemplars;
    }

    public void setExemplars(int exemplars) {
        this.exemplars = exemplars;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return id == other.id && title.equals(other.title) && categoryId == other.categoryId && exemplars == other.exemplars;
    }
}

