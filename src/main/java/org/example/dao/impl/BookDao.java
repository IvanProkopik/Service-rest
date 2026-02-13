package org.example.Dao.impl;

import org.example.Dao.Dao;
import org.example.entity.Book;

import java.util.List;
import java.util.Optional;

public class BookDao {

    private static final BookDao INSTANCE = new BookDao();

    private BookDao() {

    }

    public static BookDao getInstance() {
        return INSTANCE;
    }


}
