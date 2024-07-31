package com.example.demo_backend;


import com.example.demo_backend.dao.BookDAO;
import com.example.demo_backend.dao.BookDAOJpaImpl;
import com.example.demo_backend.entity.Book;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BookDAOTests {

    @Autowired
    private EntityManager entityManager;

    private BookDAO bookDAO;

    @BeforeEach
    public void setUp() {
        bookDAO = new BookDAOJpaImpl(entityManager);
    }

    @Test
    public void testSave() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Author");

        Book savedBook = bookDAO.save(book);

        assertThat(savedBook.getId()).isEqualTo(1L);
    }


    @Test
    public void testFindById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Author");

        entityManager.persist(book);

        Optional<Book> foundBook = bookDAO.findById(book.getId());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void testFindById_NotFound(){
        Optional<Book> notFoundBook = bookDAO.findById(5);
        assertThat(notFoundBook).isNotPresent();
    }

    @Test
    public void testFindAll() {
        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        book1.setAuthor("Author 1");

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Author 2");

        entityManager.persist(book1);
        entityManager.persist(book2);

        List<Book> books = bookDAO.findAll();

        assertThat(books).hasSize(2);
    }

    @Test
    public void testDeleteById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Author");

        entityManager.persist(book);

        Optional<Book> deletedBook = bookDAO.deleteById(book.getId());

        assertThat(deletedBook).isPresent();

        Optional<Book> foundBook = bookDAO.findById(book.getId());

        assertThat(foundBook).isNotPresent();
    }

    @Test
    public void testDeleteById_NotFound(){
        Optional<Book> notFoundBook = bookDAO.deleteById(5);
        assertThat(notFoundBook).isNotPresent();
    }
}