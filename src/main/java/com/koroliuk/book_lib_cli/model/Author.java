package com.koroliuk.book_lib_cli.model;

public class Author {
    int id;
    String name;
    public Author(int id, String name) {
        this.id = id;
        this.name= name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
