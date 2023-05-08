CREATE TABLE Author (
    id bigint PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE Category (
    id int PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE Book (
    id bigint PRIMARY KEY,
    title varchar(255) NOT NULL,
    category_id int NOT NULL,
    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
            REFERENCES Category(id)
                  ON DELETE CASCADE
);

CREATE TABLE Book_Author (
    book_id bigint NOT NULL,
    author_id bigint NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES Book(id) ON DELETE CASCADE,
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES Author(id) ON DELETE CASCADE
);

CREATE TABLE User {
    id bigint PRIMARY KEY,
    name varchar(255) NOT NULL
};

CREATE TABLE Checkout {
    id bigint PRIMARY KEY,
    start_time DATA, NOT NULL
    end_time DATA, NOT NULL
    user_id int NOT NULL,
    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES User(id)
                ON DELETE CASCADE
    isReturned BOOLEAN
};