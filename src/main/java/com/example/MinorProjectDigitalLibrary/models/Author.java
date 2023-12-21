package com.example.MinorProjectDigitalLibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = "bookList") if you want to see only Author
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String country;

    private String email;
    @CreationTimestamp
    private Date addedOn;
    @OneToMany(mappedBy = "my_author")
    @JsonIgnoreProperties(value = "my_author")
    private List<Book> bookList;
    // By JsonIgnoreProperties we can ignore my_author in bookList as it is not relevant to us and same way we can ignore bookList in Book table of Author
  /*  {
        "id": 1,
            "name": "Iqbal",
            "country": "India",
            "email": "Iqbal@gmail.com",
            "addedOn": "2023-11-07T12:28:28.393+00:00",
            "bookList": [
        {
            "id": 1,
                "name": "Into to Python",
                "genre": "PROGRAMMING",
                "pages": 500,
                "createdOn": "2023-11-07T12:28:28.545+00:00",
                "updatedOn": "2023-11-07T12:28:28.545+00:00",

       JsonIgnoreProperties(my_author) =>         "my_author": {
            "id": 1,
                    "name": "Iqbal",
                    "country": "India",
                    "email": "Iqbal@gmail.com",
                    "addedOn": "2023-11-07T12:28:28.393+00:00"
        },
            "my_student": null,
                "transactionList": []
        }
        ]
    }*/
}
