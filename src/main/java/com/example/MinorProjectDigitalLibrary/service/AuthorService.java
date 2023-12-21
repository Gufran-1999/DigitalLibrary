package com.example.MinorProjectDigitalLibrary.service;

import com.example.MinorProjectDigitalLibrary.models.Author;
import com.example.MinorProjectDigitalLibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author createIfNotPresent(Author author){
        Author authorFromDB = this.authorRepository.findByEmail(author.getEmail());
        if(authorFromDB != null){
            return authorFromDB;
        }

        return this.authorRepository.save(author);
    }

    public List<Author> getAuthorList() {
        return authorRepository.findAll();
    }
}
