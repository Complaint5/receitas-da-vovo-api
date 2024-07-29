package com.receitas_da_vovo.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.dtos.CommentDto;
import com.receitas_da_vovo.dtos.SaveCommentDto;
import com.receitas_da_vovo.services.CommentService;

import jakarta.validation.Valid;

/**
 * Classe reponsável pelos endpoints para comentarios
 */
@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * Método responsável pelo endpoint de salvar um comentario
     * 
     * @param saveCommentDto recebe um objeto do tipo SaveCommentDto
     * @return retorna um ResponseEntity do tipo CommentDto com o estatos created
     */
    @PostMapping
    public ResponseEntity<CommentDto> saveComment(@RequestBody @Valid SaveCommentDto saveCommentDto) {
        CommentDto comment = this.commentService.saveComment(saveCommentDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.id())
                .toUri();

        return ResponseEntity.created(uri).body(comment);
    }

    /**
     * Método responsável pelo endpoint de atualizar um comentario
     * 
     * @param id recebe um UUID
     * @param commentDto recebe um objeto do tipo CommentDto
     * @return retorna um ResponseEntity do tipo CommentDto com o estatos ok
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable UUID id, @RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.ok(this.commentService.updateComment(id, commentDto));
    }

    /**
     * Método responsável pelo endpoint de deletar um comentario
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com o estatos no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        this.commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método responsável pelo endpoint de retornar um comentario com base no id
     * @param id recebe um UUID
     * @return retorna um ResponseEntity do tipo CommentDto com o estatos ok
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.commentService.findCommentById(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma lista de todos os comentario
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com uma lista do tipo CommentDto com o estatos ok
     */
    @GetMapping("/recipe/{id}")
    public ResponseEntity<List<CommentDto>> findAllByRecipe(@PathVariable UUID id) {
        return ResponseEntity.ok(this.commentService.findAllCommentsByRecipe(id));
    }
}
