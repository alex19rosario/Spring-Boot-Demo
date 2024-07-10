package com.example.demo_backend.service;

import com.example.demo_backend.dao.BookDAO;
import com.example.demo_backend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookDAO dao;

    @Autowired
    public BookServiceImpl(@Qualifier("bookDAOJpaImpl") BookDAO dao){
        this.dao = dao;
    }

    @Transactional
    @Override
    public Book save(Book book) {
        return dao.save(book);
    }

    @Override
    public Book get(long id) {
        return dao.findById(id);
    }

    @Override
    public List<Book> getAll() {
        return dao.findAll();
    }

    @Transactional
    @Override
    public Book delete(long id) {
        return dao.deleteById(id);
    }
}
