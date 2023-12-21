package com.example.MinorProjectDigitalLibrary.service;

import com.example.MinorProjectDigitalLibrary.Exception.BookException;
import com.example.MinorProjectDigitalLibrary.dto.CreateBookRequest;
import com.example.MinorProjectDigitalLibrary.dto.SearchBookRequest;
import com.example.MinorProjectDigitalLibrary.dto.UpdateBookRequest;
import com.example.MinorProjectDigitalLibrary.models.Author;
import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.models.Student;
import com.example.MinorProjectDigitalLibrary.repository.AuthorRepository;
import com.example.MinorProjectDigitalLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorService authorService;

    public Book create(@RequestBody @Valid CreateBookRequest createBookRequest){
        Book book = createBookRequest.to();
        Author author = authorService.createIfNotPresent(book.getMy_author());
        book.setMy_author(author);
        return bookRepository.save(book);
    }

    public List<Book> getBookList() {
        return bookRepository.findAll();
    }
   // @Transactional
    public Book updateIfExists(UpdateBookRequest updateBookRequest, int bookId) {
        Book oldBookFromDb = bookRepository.findById(bookId).orElse(null);
        Book book = updateBookRequest.to(oldBookFromDb,oldBookFromDb.getMy_author());
        if(oldBookFromDb!=null){
            bookRepository.save(book);
        }
        return book;
    }

    public String deleteBookIfExists(int bookId) {
        if(bookRepository.findById(bookId).orElse(null)!=null) {
            bookRepository.deleteById(bookId);
            return "Book deleted Successfully";
        }
        return "Book doesn't exists";
    }

    public List<Book> search(SearchBookRequest searchBookRequest) {
        switch (searchBookRequest.getSearchKey()){
            case "name":
                if(searchBookRequest.isAvailable()){
                    return bookRepository.findByNameAndMy_StudentIsNull(searchBookRequest.getSearchValue());
                }
                return bookRepository.findByName(searchBookRequest.getSearchValue());

            case "genre":
                return bookRepository.findByGenre(searchBookRequest.getSearchValue());

            case "id":{
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);
            }

            default:
                throw new BookException("invalid Search Key");

        }
    }

    public void assignBookToStudent(Book book, Student student) {
        bookRepository.assignBookToStudent(book.getId(),student.getId());
    }

    public void unassignBookFromStudent(Book book) {
        bookRepository.unassignBookFromStudent(book.getId());
    }
}
