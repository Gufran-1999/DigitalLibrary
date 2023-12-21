package com.example.MinorProjectDigitalLibrary.dto;

import com.example.MinorProjectDigitalLibrary.models.Author;
import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.models.enums.Genre;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {
    @NotBlank
    private String name;
    @NonNull
    private Genre genre;
    @NonNull
    private int pages;
    @NotBlank
    private String authorName;
    @NotBlank
    private String authorCountry;
    @NotBlank
    private String authorEmail;


    public Book to(){
        Author author = Author.builder()
                .name(this.authorName).country(this.authorCountry).email(this.authorEmail)
                .build();
        Book book = Book.builder()
                .name(this.name).pages(this.pages).genre(this.genre).
                 my_author(author)
                .build();
        return book;
    }
}
