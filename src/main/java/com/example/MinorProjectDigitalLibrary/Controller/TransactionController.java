package com.example.MinorProjectDigitalLibrary.Controller;

import com.example.MinorProjectDigitalLibrary.models.Transaction;
import com.example.MinorProjectDigitalLibrary.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PostMapping("/issue")
    public String issueTxn(@RequestParam("name") String name,@RequestParam("studentId") int studentId){
       return transactionService.createIssueTxn(name,studentId);
    }
    @PostMapping("/return")
    public String returnTxn(@RequestParam("bookId") int bookId,@RequestParam("studentId") int studentId){
        return transactionService.createReturnTxn(bookId,studentId);
    }

}
