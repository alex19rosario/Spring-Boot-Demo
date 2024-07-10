package com.example.demo_backend.dao;

import com.example.demo_backend.entity.Book;

import java.util.List;

public interface BookDAO {
    Book save(Book book);
    Book findById(long id);
    List<Book> findAll();
    Book deleteById(long id);

}
