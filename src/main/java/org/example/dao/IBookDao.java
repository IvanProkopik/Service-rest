package org.example.dao;

import java.util.Optional;

public interface IBookDao<K, T> extends Dao<K, T> {

    Optional<T> findName(String name);

}
