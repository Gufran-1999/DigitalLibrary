package com.example.MinorProjectDigitalLibrary.dto;

import com.example.MinorProjectDigitalLibrary.models.Student;
import com.example.MinorProjectDigitalLibrary.models.enums.TransactionType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequest {
    private String studentName;
    private String studentContact;

    public Student to(){
        Student student = Student.builder().
                 name(this.studentName).validity(new Date(System.currentTimeMillis()+365*24*60*60*1000l)).contact(this.studentContact).build();
        return student;
    }
}
