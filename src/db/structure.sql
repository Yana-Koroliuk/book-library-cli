CREATE TABLE Author (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE Category (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE Book (
    id bigserial PRIMARY KEY,
    title varchar(255) NOT NULL,
    category_id int NOT NULL,
    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
            REFERENCES Category(id)
                ON DELETE CASCADE,
    exemplars bigint
);

CREATE TABLE Book_Author (
    book_id bigint NOT NULL,
    author_id bigint NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES Book(id) ON DELETE CASCADE,
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES Author(id) ON DELETE CASCADE
);

CREATE TABLE "User" (
    id   bigserial PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE "Order" (
    id bigserial PRIMARY KEY,
    start_time DATE NOT NULL,
    end_time DATE NOT NULL,
    user_id int NOT NULL,
    book_id int NOT NULL,
    is_returned BOOLEAN,
    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES "User"(id)
                ON DELETE CASCADE,
    CONSTRAINT fk_book_id
        FOREIGN KEY (book_id)
            REFERENCES Book(id)
                ON DELETE CASCADE
);
