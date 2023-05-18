INSERT INTO Author(name) VALUES ('Aurelius');
INSERT INTO Author (name) VALUES ('Stephen King');
INSERT INTO Author (name) VALUES ('George Orwell');

INSERT INTO Category(name) VALUES ('philosophy');
INSERT INTO Category (name) VALUES ('horror');
INSERT INTO Category (name) VALUES ('dystopian');

INSERT INTO Book(title, category_id, exemplars) VALUES ('Meditation', 1, 3);
INSERT INTO Book (title, category_id, exemplars) VALUES ('The Shining', 2, 10);
INSERT INTO Book (title, category_id, exemplars) VALUES ('1984', 3, 5);

INSERT INTO Book_Author (book_id, author_id) VALUES (1, 1);
INSERT INTO Book_Author (book_id, author_id) VALUES (2, 2);
INSERT INTO Book_Author (book_id, author_id) VALUES (3, 3);

INSERT INTO "User"(name, password) VALUES ('Mark', '$2a$10$9CDXjneXcK7qPDqS0GJbeO0r.JrJSFMJqjRYiYXNneg/T6PnI3Uqy');

INSERT INTO "Order" (start_time, end_time, user_id, book_id, is_returned) VALUES ('2023-05-03', '2023-05-04', 1, 2, true);
INSERT INTO "Order" (start_time, end_time, user_id, book_id, is_returned) VALUES ('2023-05-05', '2023-05-06', 1, 3, false);
