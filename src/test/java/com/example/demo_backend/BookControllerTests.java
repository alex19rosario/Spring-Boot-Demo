package com.example.demo_backend;

import com.example.demo_backend.entity.Book;
import com.example.demo_backend.rest.BookController;
import com.example.demo_backend.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    private Book sampleBook;

    @BeforeEach
    public void setup() {
        // Initialize sample book data
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Sample Book");
        sampleBook.setAuthor("Author Name");
        sampleBook.setGenre("Test genre");
        List<Book> books = Arrays.asList(sampleBook);
        // Mock the service layer to return the sample book
        Mockito.when(bookService.get(anyLong())).thenReturn(Optional.of(sampleBook));
        //Mock the service layer to return a list of books
        Mockito.when(bookService.getAll()).thenReturn(books);
        //Mock the service layer to save a book
        Mockito.when(bookService.save(Mockito.any(Book.class))).thenReturn(sampleBook);

        mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
    }

    @Test
    void contextLoads() throws Exception{
        assertThat(bookController).isNotNull();
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN", "MANAGER", "ANALYST"})
    void getBookTest() throws Exception{
        long bookId = 1L;
        mockMvc.perform(get("/api/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.author").value("Author Name"))
                .andExpect(jsonPath("$.genre").value("Test genre"));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN", "MANAGER", "ANALYST"})
    void getBooksTest() throws Exception {
        long bookId = 1L;
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(bookId))
                .andExpect(jsonPath("$[0].title").value("Sample Book"))
                .andExpect(jsonPath("$[0].author").value("Author Name"))
                .andExpect(jsonPath("$[0].genre").value("Test genre"));
    }


}
