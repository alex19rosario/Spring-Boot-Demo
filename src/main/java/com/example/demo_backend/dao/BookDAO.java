package com.example.demo_backend.dao;

import com.example.demo_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookDAO{
    Book save(Book book);
    Optional<Book> findById(long id);
    List<Book> findAll();
    Optional<Book> deleteById(long id);

}
