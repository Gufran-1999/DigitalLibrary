package com.example.MinorProjectDigitalLibrary.Controller;

import com.example.MinorProjectDigitalLibrary.dto.CreateStudentRequest;
import com.example.MinorProjectDigitalLibrary.models.Student;
import com.example.MinorProjectDigitalLibrary.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("")
    public Student createStudent(@RequestBody CreateStudentRequest createStudentRequest) throws SQLException {
        return studentService.create(createStudentRequest);
    }
    @GetMapping("/all")
    public List<Student> getAll(){
        return studentService.getStudentList();
    }
}
