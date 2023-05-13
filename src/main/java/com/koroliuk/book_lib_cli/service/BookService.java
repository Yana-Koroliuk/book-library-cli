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
            if (categoryDao.existByName(category)) {
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
                    bookAuthorDao.createBookAuthor(bookId, authorId);
                }
            }
        }
        return bookId;
    }
    public Book updateBook(int bookId, List<String> authors, String bookNameNew, String category) {
        Book book = null;
        if (bookDao.existBookById(bookId)) {
            int categoryId;
            if (categoryDao.existByName(category)) {
                categoryId = categoryDao.findByName(category);
            } else {
                categoryId = categoryDao.createCategory(category);
            }
            if (bookDao.updateBook(bookId, bookNameNew, categoryId)) {
                book = new Book(bookId, bookNameNew, categoryId);
                List<Integer> authorsId = bookAuthorDao.findByBookId(bookId);
                for (int i = 0; i < authorsId.size(); i++) {
                    if (!bookAuthorDao.isAuthorUsedInOtherBooks(authorsId.get(i), bookId)) {
                        authorDao.deleteAuthorById(authorsId.get(i));
                    } else {
                        bookAuthorDao.deleteBookAuthorByBookAuthorId(bookId, authorsId.get(i));
                    }
                    if (i < authors.size()) {
                        int authorId;
                        if (authorDao.existByName(authors.get(i))) {
                            authorId = authorDao.findByName(authors.get(i));
                        } else {
                            authorId = authorDao.createAuthor(authors.get(i));
                        }
                        bookAuthorDao.createBookAuthor(bookId, authorId);
                    }
                }
                if (authors.size() > authorsId.size()) {
                    for (int i = authorsId.size(); i < authors.size(); i++) {
                        int authorId;
                        if (authorDao.existByName(authors.get(i))) {
                            authorId = authorDao.findByName(authors.get(i));
                        } else {
                            authorId = authorDao.createAuthor(authors.get(i));
                        }
                        bookAuthorDao.createBookAuthor(bookId, authorId);
                    }
                }
            }
        }
        return book;
    }


    public Book deleteBookById(int bookId) {
        Book book = null;
        String bookName = bookDao.findById(bookId);
        List<Integer> authorsId = bookAuthorDao.findByBookId(bookId);
        for (Integer authorId : authorsId) {
            if (!bookAuthorDao.isAuthorUsedInOtherBooks(authorId, bookId)) {
                authorDao.deleteAuthorById(authorId);
            } else {
                bookAuthorDao.deleteBookAuthorByBookAuthorId(bookId, authorId);
            }
        }
        int categoryId = bookDao.findCategoryId(bookId);
        if (bookDao.deleteBookById(bookId)) {
            book = new Book(bookId, bookName, categoryId);
        }
        return book;
    }
}

