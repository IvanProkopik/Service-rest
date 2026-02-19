package org.example.service;

import org.example.dao.impl.AuthorDao;
import org.example.dao.impl.BookDao;
import org.example.dto.AuthorDto;
import org.example.dto.BookDto;
import org.example.entity.Author;
import org.example.entity.Book;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    private static final AuthorService INSTANCE = new AuthorService();
    private static final AuthorDao authorDao = AuthorDao.getInstance();

    private AuthorService(){

    }

    public static AuthorService getInstance(){
        return INSTANCE;
    }

    public List<AuthorDto> findAll(){
        return authorDao.findAll().stream()
                .map(this::buildAuthorDto)
                .toList();
    }



    public Optional<Author> findById(Long id){
        return authorDao.findById(id);
    }

    public boolean delete(Long id){
        return authorDao.delete(id);
    }

    public Author save(Author author){
        return authorDao.save(author);
    }

    public void update(Author author){
        authorDao.update(author);
    }

    private AuthorDto buildAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getPhone(),
                author.getGmail()
        );
    }
}
