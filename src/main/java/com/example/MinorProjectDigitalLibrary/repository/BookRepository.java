package com.example.MinorProjectDigitalLibrary.repository;

import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByName(String bookName);
//    Book findFirstByNameAndIsAvailable(String bookName, boolean b);

    @Query(nativeQuery = true,value = "select * from book b where b.name= ?1 and b.is_available= ?2 limit 1")
    Book findBookInDbLimit1(String bookName, boolean b);

    @Query(nativeQuery = true,value="select * from book b where b.name= ?1 and b.my_student_id is null")
    List<Book> findByNameAndMy_StudentIsNull(String searchKeyValue);

    List<Book> findByGenre(String searchValue);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update book b set b.my_student_id = ?2 where b.id= ?1 and b.my_student_id is null")
    void assignBookToStudent(int bookId, int studentId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update book b set b.my_student_id = null where b.id= ?1")
    void unassignBookFromStudent(int bookId);
}
