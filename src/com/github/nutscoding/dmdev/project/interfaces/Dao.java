package com.github.nutscoding.dmdev.project.interfaces;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    E save(E userDetails);

    boolean delete(K id);

    void update(E userDetails);

    List<E> findAll();

    Optional<E> findById(K id);
}
