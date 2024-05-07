package picpay.desafio.picpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import picpay.desafio.picpay.domain.transaction.Transaction;
import picpay.desafio.picpay.dtos.TransactionDTO;
import picpay.desafio.picpay.services.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Transaction newTransaction = transactionService.createTransaction(transactionDTO);

        return new ResponseEntity<Transaction>(newTransaction, HttpStatus.OK);
    }
}
