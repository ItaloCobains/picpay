package picpay.desafio.picpay.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import picpay.desafio.picpay.domain.transaction.Transaction;
import picpay.desafio.picpay.domain.user.User;
import picpay.desafio.picpay.dtos.TransactionDTO;
import picpay.desafio.picpay.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = userService.findUserById(transactionDTO.senderId());
        User receiver = userService.findUserById(transactionDTO.senderId());

        userService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = authorizeTransaction(sender, transactionDTO.value());

        if (!isAuthorized) {
            throw new Exception("Transaction not authorized");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        transactionRepository.save(transaction);
        
        userService.saveUser(sender);
        userService.saveUser(receiver);
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(
                "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", Map.class);
    
        if (authorizationResponse.getStatusCode() != HttpStatus.OK) {
            return false;
        }
    
        Map<String, Object> responseBody = authorizationResponse.getBody();
        if (responseBody != null) {
            Object message = responseBody.get("message");
            if (message instanceof Boolean) {
                return (Boolean) message;
            }
        }
    
        return false;
    }
    
}
