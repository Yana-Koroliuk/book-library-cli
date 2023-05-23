package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.dao.BookAuthorDao;
import com.koroliuk.book_lib_cli.dao.BookDao;
import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Book;

import java.util.List;

public class BookService {
    private static BookDao bookDao;
    private static CategoryDao categoryDao;
    private static AuthorDao authorDao;
    private static BookAuthorDao bookAuthorDao;

    public BookService(BookDao bookDao, AuthorDao authorDao, CategoryDao categoryDao, BookAuthorDao bookAuthorDao) {
        BookService.bookDao = bookDao;
        BookService.authorDao = authorDao;
        BookService.categoryDao = categoryDao;
        BookService.bookAuthorDao = bookAuthorDao;
    }

    public Book createBook(List<String> authors, String bookName, String category, int exemplars) {
        Book book = null;
        int categoryId;
        if (categoryDao.existByName(category)) {
            categoryId = categoryDao.findByName(category);
        } else {
            categoryId = categoryDao.createCategory(category);
        }
        int bookId = bookDao.createBook(bookName, categoryId, exemplars);
        book = new Book(bookId, bookName, categoryId, exemplars);
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
        return book;
    }

    public Book updateBook(int bookId, List<String> authors, String bookNameNew, String category, int exemplars) {
        Book book = null;
        if (bookDao.existBookById(bookId)) {
            int categoryId;
            if (categoryDao.existByName(category)) {
                categoryId = categoryDao.findByName(category);
            } else {
                categoryId = categoryDao.createCategory(category);
            }
            if (bookDao.updateBook(bookId, bookNameNew, categoryId, exemplars)) {
                book = new Book(bookId, bookNameNew, categoryId, exemplars);
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

    public boolean deleteBookById(int bookId) {
        List<Integer> authorsId = bookAuthorDao.findByBookId(bookId);
        for (Integer authorId : authorsId) {
            if (!bookAuthorDao.isAuthorUsedInOtherBooks(authorId, bookId)) {
                authorDao.deleteAuthorById(authorId);
            } else {
                bookAuthorDao.deleteBookAuthorByBookAuthorId(bookId, authorId);
            }
        }
        if (bookDao.existBookById(bookId)) {
            bookDao.deleteBookById(bookId);
            return true;
        }
        return false;
    }

    public List<List<String>> searchBookByTitleAuthorCategory(String title, String author, String category) {
        return bookDao.findBookByTitleAuthorCategory(title, author, category);
    }

    public int findBookExemplars(int bookId) {
        if (bookDao.existBookById(bookId)) {
            int exemplars = bookDao.findExemplarsByBookId(bookId);
            if (exemplars > 0) {
                return exemplars;
            }
        }
        return 0;
    }
}

