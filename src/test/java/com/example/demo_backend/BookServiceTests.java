package com.example.demo_backend;

import com.example.demo_backend.dao.BookDAO;
import com.example.demo_backend.entity.Book;
import com.example.demo_backend.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setGenre("Test Genre");
    }

    @Test
    public void testSave() {
        when(bookDAO.save(any(Book.class))).thenReturn(testBook);

        Book savedBook = bookService.save(new Book());
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Test Book");

        verify(bookDAO, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetById() {
        when(bookDAO.findById(1L)).thenReturn(Optional.of(testBook));

        Optional<Book> foundBook = bookService.get(1L);
        assertTrue(foundBook.isPresent());
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");

        verify(bookDAO, times(1)).findById(1L);
    }

    @Test
    public void testGetById_NotFound(){
       Optional<Book> notFoundBook = bookService.get(2L);
       assertTrue(notFoundBook.isEmpty());
    }

    @Test
    public void testGetAll() {
        when(bookDAO.findAll()).thenReturn(Arrays.asList(testBook));

        List<Book> books = bookService.getAll();
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book");

        verify(bookDAO, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        when(bookDAO.deleteById(1L)).thenReturn(Optional.of(testBook));

        Optional<Book> deletedBook = bookService.delete(1L);
        assertTrue(deletedBook.isPresent());
        assertThat(deletedBook.get().getTitle()).isEqualTo("Test Book");

        verify(bookDAO, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_NotFound(){
        Optional<Book> notFoundBook = bookService.delete(2L);
        assertTrue(notFoundBook.isEmpty());
    }
}
