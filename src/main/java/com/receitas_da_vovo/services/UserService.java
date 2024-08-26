package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.user.SaveUserResponse;
import com.receitas_da_vovo.domain.user.UpdateUserRequest;
import com.receitas_da_vovo.domain.user.UpdateUserResponse;
import com.receitas_da_vovo.domain.user.SaveUserRequest;
import com.receitas_da_vovo.domain.user.User;
import com.receitas_da_vovo.domain.user.UserResponse;
import com.receitas_da_vovo.domain.user.UserRole;
import com.receitas_da_vovo.infra.exceptions.UserForbiddenException;
import com.receitas_da_vovo.infra.exceptions.UserNotFoundException;
import com.receitas_da_vovo.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe responsálve pela logica relacionada a User
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO: Adicionar metodo para trocar de senha
    // TODO: talvez refazer userResponse

    /**
     * Método responsável pela logica de salvar um usuario no banco de dados
     * 
     * @param SaveUserRequest recebe um objeto do tipo SaveUserRequest
     * @return retorna um objeto do tipo SaveUserResponse
     */
    @Transactional
    public SaveUserResponse saveUser(SaveUserRequest saveUserRequest) {
        User user = User.builder()
                .name(saveUserRequest.name())
                .email(saveUserRequest.email())
                .password(this.passwordEncoder.encode(saveUserRequest.password()))
                .activated(true)
                .userRole(UserRole.STANDART)
                .build();

        User userCreated = this.userRepository.save(user);

        log.info("usuairo {} foi salvo no banco de dados.", userCreated.getId());

        return new SaveUserResponse(userCreated.getId(), userCreated.getName(), userCreated.getEmail());
    }

    /**
     * Método responsável pela logica de atualizar um usuario no banco de dados
     * 
     * @param id                     recebe um UUID
     * @param UpdateUserRequest      recebe um objeto do tipo UpdateUserRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um objeto do tipo UpdateUserResponse
     */
    @Transactional
    public UpdateUserResponse updataUser(UUID id, UpdateUserRequest updateUserRequest, JwtAuthenticationToken token) {
        if (!UUID.fromString(token.getName()).equals(id)) {
            throw new UserForbiddenException();
        }
        User user = this.findUser(id);

        user.setName(updateUserRequest.name());
        user.setEmail(updateUserRequest.email());

        User userUpdated = this.userRepository.save(user);

        log.info("usuairo {} foi atualizado no banco de dados.", userUpdated.getId());

        return new UpdateUserResponse(userUpdated.getId(), userUpdated.getName(), userUpdated.getEmail());

    }

    /**
     * Método responsável pela logica de exclusão de um usuario do banco de dados
     * 
     * @param id                     recebe um UUID
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteUser(UUID id, JwtAuthenticationToken token) {
        User user = this.findUser(id);

        if (!UUID.fromString(token.getName()).equals(id)) {
            throw new UserForbiddenException();
        }

        user.setActivated(false);

        log.info("usuairo {} foi desativado no banco de dados.", user.getId());

        return true;
    }

    /**
     * Método responsável pela logica de buscar um usuario no banco de dados pelo id
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo UserResponse
     */
    public UserResponse findUserById(UUID id) {
        User user = this.findUser(id);

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    /**
     * Método responsável pela logica de buscar todos os usuario no banco de dados
     * 
     * @return retorna uma lista de objetos do tipo UserResponse
     */
    public List<UserResponse> findAllUsers() {
        return this.userRepository.findAllUsersByActivatedTrue().stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    /**
     * Método responsável pela logica de buscar um usuario no banco de dados
     * caso o coontrario lançar uma exeção
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo User
     * @throws exception Lançara uma exeção caso não encontre o usuario
     */
    public User findUser(UUID id) throws RuntimeException {
        return this.userRepository.findUserByIdAndActivatedTrue(id).orElseThrow(() -> new UserNotFoundException());
    }
}
