package picpay.desafio.picpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import picpay.desafio.picpay.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{}
