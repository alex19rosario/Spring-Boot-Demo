package com.example.demo_backend.rest;

import com.example.demo_backend.Exceptions.BookNotFoundException;
import com.example.demo_backend.entity.Book;
import com.example.demo_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(@Qualifier("bookServiceImpl") BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getAll();
    }

    @GetMapping("/books/{bookId}")
    public Book getBook(@PathVariable long bookId){
        return bookService.get(bookId).orElseThrow(() -> {
            throw new BookNotFoundException(String.format("No book associated with id %s", bookId));
        });
    }

    @PostMapping("/books")
    public Book saveBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @DeleteMapping("/books/{bookId}")
    public Book deleteBook(@PathVariable long bookId){
        return bookService.delete(bookId).orElseThrow(() -> {
            throw new BookNotFoundException(String.format("No book associated with id %s", bookId));
        });
    }


}
