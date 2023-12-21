package com.example.MinorProjectDigitalLibrary.models;

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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String contact;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
    private Date validity;
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties(value = {"my_student","my_book"})
    private List<Transaction> transactionList;
    @OneToMany(mappedBy = "my_student")
    @JsonIgnoreProperties(value = {"my_student","my_book","transactionList","available"})
    private List<Book> bookList;
}
