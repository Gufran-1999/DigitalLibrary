package com.example.MinorProjectDigitalLibrary.service;

import com.example.MinorProjectDigitalLibrary.dto.CreateStudentRequest;
import com.example.MinorProjectDigitalLibrary.models.Student;
import com.example.MinorProjectDigitalLibrary.repository.BookRepository;
import com.example.MinorProjectDigitalLibrary.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BookRepository bookRepository;
    @Transactional
    public Student create(CreateStudentRequest createStudentRequest) throws SQLException {
      return studentRepository.save(createStudentRequest.to());
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId).get();
    }
}
