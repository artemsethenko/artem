package org.example.services;

import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface CRUDServices <T> {
    ResponseEntity getById(long id);

    Collection<T> getAll();

    ResponseEntity create(T news);

    ResponseEntity update( T news);

    ResponseEntity deleteById(long id);
}
