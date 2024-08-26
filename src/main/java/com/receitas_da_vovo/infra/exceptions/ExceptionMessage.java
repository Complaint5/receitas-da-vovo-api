package com.receitas_da_vovo.infra.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe responsável por a mensagen de exeção retornada pela api
 */
@AllArgsConstructor
@Data
@Builder
public class ExceptionMessage {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}