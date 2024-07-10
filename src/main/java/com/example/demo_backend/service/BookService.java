package com.example.demo_backend.service;

import com.example.demo_backend.entity.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    Book get(long id);
    List<Book> getAll();
    Book delete(long id);
}
