package com.example.demo_backend.dao;

import com.example.demo_backend.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDAOJpaImpl implements BookDAO{

    private final EntityManager entityManager;

    @Autowired
    public BookDAOJpaImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public Book save(Book book) {
        return entityManager.merge(book);
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("from Book", Book.class);
        return query.getResultList();
    }

    @Override
    public Optional<Book> deleteById(long id) {
        Book book = entityManager.find(Book.class, id);
        if(book != null)
            entityManager.remove(book);
        return Optional.ofNullable(book);
    }
}
