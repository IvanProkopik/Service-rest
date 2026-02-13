package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K,T> {

    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    void update(K id);

    T save(T entity);

}
