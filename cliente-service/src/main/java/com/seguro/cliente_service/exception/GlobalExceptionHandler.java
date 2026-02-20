package com.seguro.cliente_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<?>> handleNotFound(ResourceNotFoundException ex) {
        return Mono.just(ResponseEntity.status(404).body(Map.of("error", ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<?>> handleValidation(MethodArgumentNotValidException ex) {
        var errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getField(),
                        e -> e.getDefaultMessage(),
                        (a, b) -> a
                ));
        return Mono.just(ResponseEntity.badRequest().body(Map.of("errors", errores)));
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<?>> handleRuntime(RuntimeException ex) {
        return Mono.just(ResponseEntity.badRequest().body(Map.of("error", ex.getMessage())));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<?>> handleValidation(WebExchangeBindException ex) {

        Map<String, String> errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getField(),
                        e -> e.getDefaultMessage(),
                        (a, b) -> a
                ));

        return Mono.just(
                ResponseEntity.badRequest().body(Map.of("errors", errors))
        );
    }
}