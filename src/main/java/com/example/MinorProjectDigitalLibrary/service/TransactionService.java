package com.example.MinorProjectDigitalLibrary.service;

import com.example.MinorProjectDigitalLibrary.Exception.BookException;
import com.example.MinorProjectDigitalLibrary.dto.SearchBookRequest;
import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.models.Student;
import com.example.MinorProjectDigitalLibrary.models.Transaction;
import com.example.MinorProjectDigitalLibrary.models.enums.TransactionStatus;
import com.example.MinorProjectDigitalLibrary.models.enums.TransactionType;
import com.example.MinorProjectDigitalLibrary.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    @Value("${student.issue.max_books}")
    private int maxBooksForIssuance;

    @Value("${student.issue.number_of_days}")
    private int numberOfDaysForIssuance;


    @Transactional
    public void create(Book bookFromDb, TransactionType transactionType) {
        Transaction transaction = Transaction.builder().book(bookFromDb).transactionType(TransactionType.ISSUE).student(bookFromDb.getMy_student()).transactionStatus(TransactionStatus.SUCCESS).build();
        transactionRepository.save(transaction);
    }

    public String createIssueTxn(String bookName,int studentId) {

        List<Book> bookList;
        try{
            bookList = bookService.search(SearchBookRequest.builder().
                    searchKey("name").searchValue(bookName)
                    .available(true).build());
        }catch(Exception e){
            throw new BookException("Book not found");
        }
       Student student = studentService.getStudentById(studentId);

        if(student.getBookList()!=null && student.getBookList().size()>= maxBooksForIssuance){
            throw new BookException("Book limit Reached");
        }
        if(bookList.isEmpty()){
            throw new BookException("Unable to find any book in library");
        }
        Book book = bookList.get(0);
        Transaction transaction = Transaction.builder().transactionType(TransactionType.ISSUE)
                 .student(student).book(book)
                .transactionStatus(TransactionStatus.PENDING).
                externalTxnId(UUID.randomUUID().toString()).build();

        transaction = transactionRepository.save(transaction);

        try{
            book.setMy_student(student);
            bookService.assignBookToStudent(book, student);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
           return transactionRepository.save(transaction).getExternalTxnId();
        }

        }

    public String createReturnTxn(int bookId, int studentId) {
      Book book;
      try{
         book = bookService.search(SearchBookRequest.builder().
                  searchKey("id").searchValue(String.valueOf(bookId)).build()).get(0);
      }catch(Exception e){
          throw new BookException("not able to fetch book details");
      }
      if(book.getMy_student()==null||book.getMy_student().getId()!=studentId)
      {
          throw new BookException("Book is not assigned to this student");
      }
      Student student = this.studentService.getStudentById(studentId);

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);

        Transaction issueTransaction = this.transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(student, book, TransactionType.ISSUE, TransactionStatus.SUCCESS);

        long issueTxnInMillis;
        try{
            issueTxnInMillis = issueTransaction.getTransactionTime().getTime();
        }catch (Exception e){
            throw new BookException("Book not issued");
        }

        long currentTimeMillis = System.currentTimeMillis();

        long timeDifferenceInMillis = currentTimeMillis - issueTxnInMillis;

        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMillis, TimeUnit.MILLISECONDS);

        Double fine = 0.0;
        if(timeDifferenceInDays > numberOfDaysForIssuance){
            fine = (timeDifferenceInDays - numberOfDaysForIssuance) * 1.0;
        }

        try{
            book.setMy_student(null);
            bookService.unassignBookFromStudent(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transaction.getExternalTxnId();
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            transaction.setFine(fine);
            return transactionRepository.save(transaction).getExternalTxnId();
        }
    }
}
