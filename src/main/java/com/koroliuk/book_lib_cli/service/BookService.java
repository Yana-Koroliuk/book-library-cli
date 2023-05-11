package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.dao.BookAuthorDao;
import com.koroliuk.book_lib_cli.dao.BookDao;
import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Book;

import java.util.List;

public class BookService {
    BookDao bookDao = new BookDao();
    CategoryDao categoryDao = new CategoryDao();
    AuthorDao authorDao = new AuthorDao();
    BookAuthorDao bookAuthorDao = new BookAuthorDao();
    public int createBook(List<String> authors, String bookName, String category) {
        int bookId = 0;
        if (!bookDao.existBook(bookName)) {
            int categoryId;
            if (categoryDao.existCategory(category)) {
                categoryId = categoryDao.findByName(category);
            } else {
                categoryId = categoryDao.createCategory(category);
            }
            bookId = bookDao.createBook(bookName, categoryId);
            for (String author : authors) {
                int authorId;
                if (!authorDao.existByName(author)) {
                    authorId = authorDao.createAuthor(author);
                } else {
                    authorId = authorDao.findByName(author);
                }
                if (!bookAuthorDao.existBookAuthor(bookId, authorId)) {
                    bookAuthorDao.creatBookAuthor(bookId, authorId);
                }
            }
        }
        return bookId;
    }
    public Book updateBook(List<String> authors, String bookNameOld, String bookNameNew, String category) {
        Book book = null;
        if (bookDao.existBook(bookNameOld)) {
            int bookId = bookDao.findByName(bookNameOld);
            int categoryId;
            if (categoryDao.existCategory(category)) {
                categoryId = categoryDao.findByName(category);
            } else {
                categoryId = categoryDao.createCategory(category);
            }
            if (bookDao.updateBook(bookId, bookNameNew, categoryId)) {
                book = new Book(bookId, bookNameNew, categoryId);
                List<Integer> authorsId = bookAuthorDao.findByBookId(bookId);
                for (int i = 0; i < authorsId.size(); i++) {
                    if (i < authors.size()) {
                        authorDao.updateAuthor(authors.get(i), authorsId.get(i));
                    } else {
                        authorDao.deleteAuthorById(authorsId.get(i));
                        bookAuthorDao.deleteBookAuthorByAuthorId(authorsId.get(i));
                    }
                }
                if (authors.size() > authorsId.size()) {
                    for (int i = authorsId.size(); i < authors.size(); i++) {
                        int authorId = authorDao.createAuthor(authors.get(i));
                        bookAuthorDao.creatBookAuthor(bookId, authorId);
                    }
                }
            }
        }
        return book;
    }
    public Book deleteBookByTitle(List<String> authors, String bookName, String category) {
        Book book = null;
        if (bookDao.existBook(bookName)) {
            int bookId = bookDao.findByName(bookName);
            List<Integer> authorsId = bookAuthorDao.findByBookId(bookId);
            if (bookDao.deleteBookById(bookId)) {
                for (Integer authorId : authorsId) {
                    bookAuthorDao.deleteBookAuthorByAuthorId(authorId);
                    authorDao.deleteAuthorById(authorId);
                }
                int categoryId = categoryDao.findByName(category);
                book = new Book(bookId, bookName, categoryId);
            }
        }
        return book;
    }

}

