CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    book_title VARCHAR(24) NOT NULL,
    year_of_publication INTEGER NOT NULL,
    genre VARCHAR(14) NOT NULL,
    author_id BIGINT NOT NULL,
	 FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

CREATE TABLE authors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(16) NOT NULL,
    last_name VARCHAR(16) NOT NULL,
    phone NUMERIC(24, 0) NOT NULL,
    gmail VARCHAR(14) NOT NULL
);