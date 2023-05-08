INSERT INTO Author(id, name) VALUES (1, 'Aurelius');
INSERT INTO Author(id, name) VALUES (2, 'Mark Twain');

INSERT INTO Category(id, name) VALUES (1, 'philosophy');
INSERT INTO Category(id, name) VALUES (2, 'adventure');

INSERT INTO Book(id, title, category_id) VALUES (1, 'Meditation', 1);
INSERT INTO Book(id, title, category_id) VALUES (2, 'Tom Sawyer', 2);
INSERT INTO Book(id, title, category_id) VALUES (3, 'Bible', 1);
INSERT INTO Book(id, title, category_id) VALUES (4, 'Quran', 1);


INSERT INTO Book_Author (book_id, author_id) VALUES (1, 1);
INSERT INTO Book_Author (book_id, author_id) VALUES (2, 2);
INSERT INTO Book_Author (book_id, author_id) VALUES (3, 1);
INSERT INTO Book_Author (book_id, author_id) VALUES (4, 1);
