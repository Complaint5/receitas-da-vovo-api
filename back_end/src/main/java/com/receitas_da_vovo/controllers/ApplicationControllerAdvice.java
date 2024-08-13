package com.receitas_da_vovo.controllers;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.receitas_da_vovo.infra.exceptions.AuthenticationFailedException;
import com.receitas_da_vovo.infra.exceptions.CommentNotFoundException;
import com.receitas_da_vovo.infra.exceptions.ExceptionMessage;
import com.receitas_da_vovo.infra.exceptions.RecipeNotFoundException;
import com.receitas_da_vovo.infra.exceptions.UserForbiddenException;
import com.receitas_da_vovo.infra.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe responsável por capturar as exeções e retornar um ResponseEntity com o
 * status http correspondente com o erro
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

        /**
         * Método responsável por tratar a exeção RecipeNotFoundException
         * 
         * @param RecipeNotFoundException recebe uma exeção do tipo
         *                                RecipeNotFoundException
         * @param HttpServletRequest      recebe um ojbeto do tipo HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
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

        /**
         * Método responsável por tratar a exeção UserNotFoundException
         * 
         * @param UserNotFoundException recebe uma exeção do tipo
         *                              UserNotFoundException
         * @param HttpServletRequest    recebe um ojbeto do tipo HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
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

        /**
         * Método responsável por tratar a exeção CommentNotFoundException
         * 
         * @param CommentNotFoundException recebe uma exeção do tipo
         *                                 CommentNotFoundException
         * @param HttpServletRequest       recebe um ojbeto do tipo HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
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

        /**
         * Método responsável por tratar a exeção UserForbiddenException
         * 
         * @param UserForbiddenException recebe uma exeção do tipo
         *                               UserForbiddenException
         * @param HttpServletRequest     recebe um ojbeto do tipo HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
        @ExceptionHandler(UserForbiddenException.class)
        public ResponseEntity<ExceptionMessage> handlerUserForbiddenException(UserForbiddenException exception,
                        HttpServletRequest request) {

                ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.FORBIDDEN.value())
                                .error(HttpStatus.FORBIDDEN.name())
                                .message(exception.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
        }

        /**
         * Método responsável por tratar a exeção AuthenticationFailedException
         * 
         * @param AuthenticationFailedException recebe uma exeção do tipo
         *                                      AuthenticationFailedException
         * @param HttpServletRequest            recebe um ojbeto do tipo
         *                                      HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
        @ExceptionHandler(AuthenticationFailedException.class)
        public ResponseEntity<ExceptionMessage> handlerAuthenticationFailedException(
                        AuthenticationFailedException exception,
                        HttpServletRequest request) {

                ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .error(HttpStatus.UNAUTHORIZED.name())
                                .message(exception.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAUTHORIZED);
        }

        // aauthentication

        /**
         * Método responsável por tratar a exeção DataIntegrityViolationException
         * 
         * @param DataIntegrityViolationException recebe uma exeção do tipo
         *                                        DataIntegrityViolationException
         * @param HttpServletRequest              recebe um ojbeto do tipo
         *                                        HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ExceptionMessage> handlerDataIntegrityViolationException(
                        DataIntegrityViolationException exception, HttpServletRequest request) {

                ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.name())
                                .message("duplicated key")
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }

        /**
         * Método responsável por tratar a exeção MethodArgumentNotValidException
         * 
         * @param MethodArgumentNotValidException recebe uma exeção do tipo
         *                                        MethodArgumentNotValidException
         * @param HttpServletRequest              recebe um ojbeto do tipo
         *                                        HttpServletRequest
         * @return retorna um ResponseEntity do tipo ExceptionMessage com o status not
         *         found
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ExceptionMessage> handlerMethodArgumentNotValidException(
                        MethodArgumentNotValidException exception, HttpServletRequest request) {

                ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.name())
                                .message(exception.getAllErrors()+"campo inválido")
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
        }
}