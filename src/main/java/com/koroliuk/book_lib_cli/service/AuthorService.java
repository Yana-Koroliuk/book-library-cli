package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.model.Author;

public class AuthorService {
    AuthorDao authorDao = new AuthorDao();

    public int createAuthor(String authorName) {
        int authorId = 0;
        if (!authorDao.existByName(authorName)) {
            authorId =  authorDao.createAuthor(authorName);
        }
        return authorId;
    }
    public Author updateAuthor(String authorNameOld, String authorNameNew) {
        Author author = null;
        if (authorDao.existByName(authorNameOld)) {
            int authorId = authorDao.findByName(authorNameOld);
            if (authorDao.updateAuthor(authorNameNew, authorId)) {
                author = new Author(authorId, authorNameNew);
            }
         }
        return author;
    }
    public Author deleteAuthorById(String authorName) {
        Author author = null;
        if (authorDao.existByName(authorName)) {
            int authorId = authorDao.findByName(authorName);
            if (authorDao.deleteAuthorById(authorId)) {
                author = new Author(authorId, authorName);
            }
        }
        return author;
    }
}
