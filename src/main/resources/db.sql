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

-- Тестові автори
INSERT INTO authors (first_name, last_name, phone, gmail) VALUES
('George', 'Orwell', 380991112233, 'orwell@gmail.com'),
('J.K.', 'Rowling', 380992223344, 'rowling@gmail.com'),
('Stephen', 'King', 380993334455, 'king@gmail.com'),
('Agatha', 'Christie', 380994445566, 'christie@gmail.com'),
('Ernest', 'Hemingway', 380995556677, 'hemingway@gmail.com');


-- Тестові книги
INSERT INTO books (book_title, year_of_publication, genre, author_id) VALUES
('1984', 1949, 'Dystopia', 1),
('Animal Farm', 1945, 'Satire', 1),
('Harry Potter', 1997, 'Fantasy', 2),
('The Shining', 1977, 'Horror', 3),
('Murder Orient', 1934, 'Detective', 4),
('Old Man Sea', 1952, 'Classic', 5);