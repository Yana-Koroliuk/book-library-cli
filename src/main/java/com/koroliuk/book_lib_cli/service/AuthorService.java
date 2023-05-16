package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.model.Author;

public class AuthorService {
    private static AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        AuthorService.authorDao = authorDao;
    }

    public Author createAuthor(String authorName) {
        Author author = null;
        if (!authorDao.existByName(authorName)) {
            int authorId = authorDao.createAuthor(authorName);
            author = new Author(authorId, authorName);
        }
        return author;
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
