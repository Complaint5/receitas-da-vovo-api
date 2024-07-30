package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.dtos.CommentDto;
import com.receitas_da_vovo.dtos.SaveCommentDto;
import com.receitas_da_vovo.entities.CommentEntity;
import com.receitas_da_vovo.exceptions.CommentNotFoundException;
import com.receitas_da_vovo.repositories.CommentRepository;

import jakarta.transaction.Transactional;

/**
 * Classe responsável pela lógica relacionada aos comentarios
 */
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
     * @param saveCommentDto recebe um objeto do tipo SaveCommentDto
     * @return retorna um objeto do tipo CommentDto
     */
    @Transactional
    public CommentDto saveComment(SaveCommentDto saveCommentDto) {
        CommentEntity comment = CommentEntity.builder()
                .body(saveCommentDto.body())
                .recipe(this.recipeService.findRecipe(saveCommentDto.recipe().id()))
                .owner(this.userService.findUserEntity(saveCommentDto.owner().id()))
                .activated(true)
                .build();

        CommentEntity commentSaved = this.commentRepository.save(comment);

        return new CommentDto(commentSaved.getId(), commentSaved.getBody());
    }

    /**
     * Método responsável pela lógica de atualizar um comentario no banco de dados
     * 
     * @param id         recebe um UUID
     * @param mensageDto recebe um objeto do tipo CommentDto
     * @return retorna um objeto do tipo CommentDto
     */
    @Transactional
    public CommentDto updateComment(UUID id, CommentDto mensageDto) {
        CommentEntity comment = this.findCommentEntity(id);

        comment.setBody(mensageDto.body());

        CommentEntity commentUpdated = this.commentRepository.save(comment);

        return new CommentDto(commentUpdated.getId(), commentUpdated.getBody());
    }

    /**
     * Método resonsável pela lógica de exclusão um comentário no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteComment(UUID id) {
        CommentEntity comment = this.findCommentEntity(id);

        comment.setActivated(false);

        this.commentRepository.save(comment);

        return true;
    }

    /**
     * Método responsável pela lógica de buscar um comentario no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo CommentDto
     */
    public CommentDto findCommentById(UUID id) {
        CommentEntity comment = this.findCommentEntity(id);

        return new CommentDto(comment.getId(), comment.getBody());
    }

    /**
     * Método responsável pela lógica de buscar todos os comentários no banco de
     * dados com base no id da receita
     * 
     * @param id recebe um UUID
     * @return retorna uma lista de objetos CommentDto
     */
    public List<CommentDto> findAllCommentsByRecipe(UUID id) {
        System.out.println("listando tudo");
        return this.commentRepository.findAllCommentByRecipe(id).stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getBody()))
                .toList();
    }

    /**
     * Método responsável pela lógica de buscar um comentário na banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo CommentEntity
     * @throws RuntimeException pode lançar uma exeção caso não encontre o
     *                          comentário
     */
    public CommentEntity findCommentEntity(UUID id) throws RuntimeException {
        return this.commentRepository.findCommentByIdAndActivatedTrue(id)
                .orElseThrow(() -> new CommentNotFoundException());
    }
}