package com.receitas_da_vovo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.receitas_da_vovo.domain.user.User;

/**
 * Classe responsável pela lógica relacionada as querys da tabela de comentarios
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Método responsável por retornar um usuario que esteje ativo no banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um optional do tipo UserEntity
     */
    Optional<User> findUserByIdAndActivatedTrue(UUID id);

    /**
     * Método responsável por retornar todos os usuarios ativos no banco de dados
     * 
     * @return retorna um lista do tipo UserEntity
     */
    List<User> findAllUsersByActivatedTrue();

    /**
     * Método responsável por retornar um UserDetails com base no email do usuario no banco de dados
     * 
     * @param email recebe uma String
     * @return retornar um objeto do tipo UserDetails
     */
    UserDetails findUserByEmail(String email);

    /**
     * Método responsável por retornar um Optiona de User com base no email do usuario no banco de dados
     * 
     * @param email recebe uma String
     * @return retorna um Optional do tipo User
     */
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findUserByEmailNativeQuery(@Param("email")String email);
}
