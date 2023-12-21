package com.example.MinorProjectDigitalLibrary.models;

import com.example.MinorProjectDigitalLibrary.models.enums.TransactionStatus;
import com.example.MinorProjectDigitalLibrary.models.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String externalTxnId;
    @CreationTimestamp
    private Date transactionTime;
    @UpdateTimestamp
    private Date updatedOn;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn
    private Book book;
    @ManyToOne
    @JoinColumn
    private Student student;

    private Double fine;

}
