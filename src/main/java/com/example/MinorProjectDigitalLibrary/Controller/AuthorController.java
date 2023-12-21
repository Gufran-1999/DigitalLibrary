package com.example.MinorProjectDigitalLibrary.Controller;

import com.example.MinorProjectDigitalLibrary.models.Author;
import com.example.MinorProjectDigitalLibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @GetMapping("/all")
    public List<Author> getAll(){
        return authorService.getAuthorList();
    }

}
