package fr.diginamic.qualiair.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestionnaire d'exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //todo per exception
    public ResponseEntity<String> handleIllegalArgumentException(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class) //todo per exception
    public ResponseEntity<String> handleFunctionnalException(FunctionnalException e) {
        System.out.println(e.getMessage()); //todo temp for debug
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
