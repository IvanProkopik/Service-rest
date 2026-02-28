package org.example.dao;

import org.example.entity.Author;

import java.util.List;
import java.util.Optional;

public interface Dao<K,T> {

    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    void update(T entity);

    void patch(T entity);

    T save(T entity);

}
