package com.receitas_da_vovo.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.receitas_da_vovo.infra.exceptions.ExceptionMessage;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    // TODO: todo erro da apliacação vem pra esse endpoit
    // TODO: talvez tirar
    @RequestMapping("/error")
    public ResponseEntity<ExceptionMessage> handleError(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(ExceptionMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message("erro ao processar requisição")
                .path(request.getRequestURI())
                .build());
    }
}