package com.example.MinorProjectDigitalLibrary.Controller;

import com.example.MinorProjectDigitalLibrary.dto.CreateBookRequest;
import com.example.MinorProjectDigitalLibrary.dto.SearchBookRequest;
import com.example.MinorProjectDigitalLibrary.dto.UpdateBookRequest;
import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("")
    public Book createBook(@Valid @RequestBody CreateBookRequest createBookRequest){
        return bookService.create(createBookRequest);
    }
    @PutMapping("/update/{bookId}")
    public Book updateBook(@Valid @RequestBody UpdateBookRequest updateBookRequest, @PathVariable("bookId") int bookId){
        return bookService.updateIfExists(updateBookRequest,bookId);
    }
    @GetMapping("/all")
    public List<Book> getAll(){
        return bookService.getBookList();
    }

    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId){
        return bookService.deleteBookIfExists(bookId);
    }
    @GetMapping("/search")
    public List<Book> getSearchBook(@RequestBody SearchBookRequest searchBookRequest){
         return bookService.search(searchBookRequest);
    }
}
