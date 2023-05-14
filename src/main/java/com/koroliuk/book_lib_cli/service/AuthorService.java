package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.model.Author;
import com.koroliuk.book_lib_cli.model.User;

public class AuthorService {
    AuthorDao authorDao = new AuthorDao();

    public int createAuthor(String authorName) {
        int authorId = 0;
        if (!authorDao.existByName(authorName)) {
            authorId =  authorDao.createAuthor(authorName);
        }
        return authorId;
    }
    public Author updateAuthor(int authorId, String authorNameNew) {
        Author author = null;
        if (authorDao.existById(authorId)) {
            if (authorDao.updateAuthor(authorId, authorNameNew)) {
                author = new Author(authorId, authorNameNew);
            }
         }
        return author;
    }
    public boolean deleteAuthorById(int authorId) {
        if (authorDao.existById(authorId)) {
            authorDao.deleteAuthorById(authorId);
            return true;
        }
        return false;
    }
}
