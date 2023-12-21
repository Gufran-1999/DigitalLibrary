package com.example.MinorProjectDigitalLibrary.dto;

import com.example.MinorProjectDigitalLibrary.models.Author;
import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.models.enums.Genre;
import lombok.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookRequest {
    @NotBlank
    private String name;
    @NonNull
    private int pages;
    @NotBlank
    private String authorCountry;
    @NotBlank
    private String authorEmail;

    public Book to(Book oldBookFromDb,Author author){
        author.setCountry(this.authorCountry);
        author.setEmail(this.authorEmail);
        oldBookFromDb.setName(this.name);
        oldBookFromDb.setPages(this.pages);
        oldBookFromDb.setMy_author(author);
        return oldBookFromDb;
    }
}
