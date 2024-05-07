package picpay.desafio.picpay.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import picpay.desafio.picpay.dtos.ExceptionDTO;

@RestControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDuplicateEntity(DataIntegrityViolationException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Entity already exists", "409");
        return ResponseEntity.status(409).body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threatEntityNotFound(EntityNotFoundException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Entity not found", "404");
        return ResponseEntity.status(404).body(exceptionDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity threatIllegalArgument(IllegalArgumentException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Invalid argument", "400");
        return ResponseEntity.status(400).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatException(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Internal server error", "500");
        return ResponseEntity.status(500).body(exceptionDTO);
    }
}