package org.example.Dao;

import java.util.List;
import java.util.Optional;

public interface BookDao<K,T> extends Dao<K,T>{

    Optional<T> findName(String name);

}
