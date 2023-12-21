package com.example.MinorProjectDigitalLibrary.repository;

import com.example.MinorProjectDigitalLibrary.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findByEmail(String email);
}
