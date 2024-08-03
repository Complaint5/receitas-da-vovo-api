package com.receitas_da_vovo.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.receitas_da_vovo.infra.exceptions.CommentNotFoundException;
import com.receitas_da_vovo.infra.exceptions.ExceptionMessage;
import com.receitas_da_vovo.infra.exceptions.RecipeNotFoundException;
import com.receitas_da_vovo.infra.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handlerRecipeNotFondException(RecipeNotFoundException exception,
            HttpServletRequest request) {
                
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handlerUserNotFondException(UserNotFoundException exception,
            HttpServletRequest request) {

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handlerCommentNotFondException(CommentNotFoundException exception,
            HttpServletRequest request) {

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }
}