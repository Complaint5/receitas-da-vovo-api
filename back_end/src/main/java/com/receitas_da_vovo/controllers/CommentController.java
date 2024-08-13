package com.receitas_da_vovo.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.domain.comment.CommentResponse;
import com.receitas_da_vovo.domain.comment.SaveCommentRequest;
import com.receitas_da_vovo.domain.comment.SaveCommentResponse;
import com.receitas_da_vovo.domain.comment.UpdateCommentRequest;
import com.receitas_da_vovo.domain.comment.UpdateCommentResponse;
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
     * @param SaveCommentRequest     recebe um objeto do tipo SaveCommentRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity do tipo SaveCommentResponse com o estatos
     *         created
     */
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<SaveCommentResponse> saveComment(@RequestBody @Valid SaveCommentRequest saveCommentRequest,
            JwtAuthenticationToken token) {
        SaveCommentResponse comment = this.commentService.saveComment(saveCommentRequest, token);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.id())
                .toUri();

        return ResponseEntity.created(uri).body(comment);
    }

    /**
     * Método responsável pelo endpoint de atualizar um comentario
     * 
     * @param id                     recebe um UUID
     * @param UpdateCommentRequest   recebe um objeto do tipo UpdateCommentRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity do tipo UpdateCommentResponse com o estatos
     *         ok
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable UUID id,
            @RequestBody @Valid UpdateCommentRequest updateCommentRequest, JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.commentService.updateComment(id, updateCommentRequest, token));
    }

    /**
     * Método responsável pelo endpoint de deletar um comentario
     * 
     * @param id recebe um UUID
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity com o estatos no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id, JwtAuthenticationToken token) {
        this.commentService.deleteComment(id, token);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método responsável pelo endpoint de retornar um comentario com base no id
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity do tipo CommentResponse com o estatos ok
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<CommentResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.commentService.findCommentById(id));
    }

    /**
     * Método responsável pelo endpoint de retornar uma lista de todos os comentario
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com uma lista do tipo CommentResponse com o
     *         estatos ok
     */
    @GetMapping("/recipe/{id}")
    public ResponseEntity<List<CommentResponse>> findAllByRecipe(@PathVariable UUID id) {
        return ResponseEntity.ok(this.commentService.findAllCommentsByRecipe(id));
    }
}
