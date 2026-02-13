package org.example.dao;

import java.util.Optional;

public interface BookDao<K,T> extends Dao<K,T>{

    Optional<T> findName(String name);

}
