package com.example.demo_backend.service;

import com.example.demo_backend.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book);
    Optional<Book> get(long id);
    List<Book> getAll();
    Optional<Book> delete(long id);
}
