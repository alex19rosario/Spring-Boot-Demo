package com.example.demo_backend.rest;

import com.example.demo_backend.entity.Book;
import com.example.demo_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final BookService bookService;

    @Autowired
    public Controller(@Qualifier("bookServiceImpl") BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getAll();
    }

    @GetMapping("/books/{bookId}")
    public Book getBook(@PathVariable long bookId){
        return bookService.get(bookId);
    }

    @PostMapping("/books")
    public Book saveBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @DeleteMapping("/books/{bookId}")
    public Book deleteBook(@PathVariable long bookId){
        return bookService.delete(bookId);
    }


}
