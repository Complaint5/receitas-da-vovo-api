package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.comment.Comment;
import com.receitas_da_vovo.domain.comment.CommentResponse;
import com.receitas_da_vovo.domain.comment.CommentRequest;
import com.receitas_da_vovo.infra.exceptions.CommentNotFoundException;
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
     * @param commentRequest recebe um objeto do tipo CommentRequest
     * @return retorna um objeto do tipo CommentResponse
     */
    @Transactional
    public CommentResponse saveComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .body(commentRequest.body())
                .recipe(this.recipeService.findRecipe(commentRequest.recipeId()))
                .owner(this.userService.findUser(commentRequest.ownerId()))
                .activated(true)
                .build();

        Comment commentSaved = this.commentRepository.save(comment);

        log.info("cometario {} foi salvo no banco de dados.", commentSaved.getId());

        return new CommentResponse(commentSaved.getId(), commentSaved.getBody());
    }

    /**
     * Método responsável pela lógica de atualizar um comentario no banco de dados
     * 
     * @param id         recebe um UUID
     * @param commentResponse recebe um objeto do tipo CommentResponse
     * @return retorna um objeto do tipo CommentResponse
     */
    @Transactional
    public CommentResponse updateComment(UUID id, CommentResponse commentResponse) {
        Comment comment = this.findComment(id);

        comment.setBody(commentResponse.body());

        Comment commentUpdated = this.commentRepository.save(comment);

        log.info("cometario {} foi atualizado no banco de dados.", commentUpdated.getId());

        return new CommentResponse(commentUpdated.getId(), commentUpdated.getBody());
    }

    /**
     * Método resonsável pela lógica de exclusão um comentário no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteComment(UUID id) {
        Comment comment = this.findComment(id);

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