package com.receitas_da_vovo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.receitas_da_vovo.domain.comment.Comment;

/**
 * Classe responsável pela lógica relacionada as querys da tabela de comentários
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    /**
     * Método responsável por retornar um comentário com base no id e se está ativo
     * no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um optional do tipo CommentEntity
     */
    Optional<Comment> findCommentByIdAndActivatedTrue(UUID id);

    /**
     * Método responsável por retornar uma lista de de comentários com base na
     * receita e que estejam ativos no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna uma lista do tipo CommentEntity
     */
    @Query(value = "SELECT * FROM comments WHERE recipe_id = :id AND activated = true", nativeQuery = true)
    List<Comment> findAllCommentByRecipe(@Param("id") UUID id);
}
