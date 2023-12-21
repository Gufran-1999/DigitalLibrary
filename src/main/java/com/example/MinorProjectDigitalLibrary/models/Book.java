package com.example.MinorProjectDigitalLibrary.models;

import com.example.MinorProjectDigitalLibrary.models.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"my_student","transactionList"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private int pages;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties(value = "bookList")
    private Author my_author;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties(value={"transactionList","bookList"})
    private Student my_student;
    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties(value = {"my_book"})
    private List<Transaction> transactionList;
}
