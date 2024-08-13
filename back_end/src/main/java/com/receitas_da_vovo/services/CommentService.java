package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.comment.Comment;
import com.receitas_da_vovo.domain.comment.CommentResponse;
import com.receitas_da_vovo.domain.comment.SaveCommentRequest;
import com.receitas_da_vovo.domain.comment.SaveCommentResponse;
import com.receitas_da_vovo.domain.comment.UpdateCommentRequest;
import com.receitas_da_vovo.domain.comment.UpdateCommentResponse;
import com.receitas_da_vovo.infra.exceptions.CommentNotFoundException;
import com.receitas_da_vovo.infra.exceptions.UserForbiddenException;
import com.receitas_da_vovo.repositories.CommentRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe responsável pela lógica relacionada aos comentarios
 */
@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    /**
     * Método responsável pela lógica de salvar um comentario no banco de dados
     * 
     * @param SaveCommentResponse    recebe um objeto do tipo SaveCommentResponse
     * @param JwtAuthenticationToken recebe um objeto do tipo
     *                               JwtAuthenticationToken
     * @return retorna um objeto do tipo SaveCommentResponse
     */
    @Transactional
    public SaveCommentResponse saveComment(SaveCommentRequest saveCommentRequest, JwtAuthenticationToken token) {
        Comment comment = Comment.builder()
                .body(saveCommentRequest.body())
                .recipe(this.recipeService.findRecipe(saveCommentRequest.recipeId()))
                .owner(this.userService.findUser(UUID.fromString(token.getName())))
                .activated(true)
                .build();

        Comment commentSaved = this.commentRepository.save(comment);

        log.info("cometario {} foi salvo no banco de dados.", commentSaved.getId());

        return new SaveCommentResponse(commentSaved.getId(), commentSaved.getBody());
    }

    /**
     * Método responsável pela lógica de atualizar um comentario no banco de dados
     * 
     * @param id                     recebe um UUID
     * @param UpdateCommentResponse  recebe um objeto do tipo UpdateCommentResponse
     * @param JwtAuthenticationToken recebe um objeto do tipo
     *                               JwtAuthenticationToken
     * @return retorna um objeto do tipo UpdateCommentResponse
     */
    @Transactional
    public UpdateCommentResponse updateComment(UUID id, UpdateCommentRequest updateCommentRequest,
            JwtAuthenticationToken token) {
        Comment comment = this.findComment(id);

        if (!UUID.fromString(token.getName()).equals(comment.getOwner().getId())) {
            throw new UserForbiddenException();
        }

        comment.setBody(updateCommentRequest.body());

        Comment commentUpdated = this.commentRepository.save(comment);

        log.info("cometario {} foi atualizado no banco de dados.", commentUpdated.getId());

        return new UpdateCommentResponse(commentUpdated.getId(), commentUpdated.getBody());
    }

    /**
     * Método resonsável pela lógica de exclusão um comentário no banco de dados
     * 
     * @param id                     recebe um UUID
     * @param JwtAuthenticationToken recebe um objeto do tipo
     *                               JwtAuthenticationToken
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteComment(UUID id, JwtAuthenticationToken token) {
        Comment comment = this.findComment(id);

        if (!UUID.fromString(token.getName()).equals(comment.getOwner().getId())) {
            throw new UserForbiddenException();
        }

        comment.setActivated(false);

        log.info("cometario {} foi desativada no banco de dados.", comment.getId());

        return true;
    }

    /**
     * Método responsável pela lógica de buscar um comentario no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo CommentResponse
     */
    public CommentResponse findCommentById(UUID id) {
        Comment comment = this.findComment(id);

        return new CommentResponse(comment.getId(), comment.getBody());
    }

    /**
     * Método responsável pela lógica de buscar todos os comentários no banco de
     * dados com base no id da receita
     * 
     * @param id recebe um UUID
     * @return retorna uma lista de objetos CommentResponse
     */
    public List<CommentResponse> findAllCommentsByRecipe(UUID id) {
        System.out.println("listando tudo");
        return this.commentRepository.findAllCommentByRecipe(id).stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getBody()))
                .toList();
    }

    /**
     * Método responsável pela lógica de buscar um comentário na banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo Comment
     * @throws RuntimeException pode lançar uma exeção caso não encontre o
     *                          comentário
     */
    public Comment findComment(UUID id) throws RuntimeException {
        return this.commentRepository.findCommentByIdAndActivatedTrue(id)
                .orElseThrow(() -> new CommentNotFoundException());
    }
}