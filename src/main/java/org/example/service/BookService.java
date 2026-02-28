package org.example.service;

import org.example.dao.impl.BookDao;
import org.example.dto.BookDto;
import org.example.entity.Book;

import java.util.List;
import java.util.Optional;

public class BookService {
    private static final BookService INSTANCE = new BookService();
    private static final BookDao bookDao = BookDao.getInstance();

    private BookService() {

    }

    public static BookService getInstance() {
        return INSTANCE;
    }

    public List<BookDto> findAll() {
        return bookDao.findAll().stream()
                .map(this::buildBookDto)
                .toList();
    }

    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);
    }

    public boolean delete(Long id) {
        return bookDao.delete(id);
    }

    public Book save(Book book) {
        return bookDao.save(book);
    }

    public void update(Book book) {
        bookDao.update(book);
    }

    public Optional<Book> findByName(String name) {
        return bookDao.findName(name);
    }

    private BookDto buildBookDto(Book book) {
        String description = String.format(
                "%s%s -> %s%s",
                book.getBookTitle(),
                book.getGenre(),
                book.getAuthorId(),
                book.getYearOfPublication()
        );
        return new BookDto(book.getId(), description);
    }

}
