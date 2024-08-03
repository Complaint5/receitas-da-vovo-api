package com.receitas_da_vovo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.user.User;
import com.receitas_da_vovo.domain.user.UserRequest;
import com.receitas_da_vovo.domain.user.UserResponse;
import com.receitas_da_vovo.domain.user.UserRole;
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

    // TODO: Adicionar metodo para trocar de senha

    /**
     * Método responsável pela logica de salvar um usuario no banco de dados
     * 
     * @param userRequest recebe um objeto do tipo UserRequest
     * @return retorna um objeto do tipo UserResponse
     */
    @Transactional
    public UserResponse saveUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .password(userRequest.password())
                .activated(true)
                .userRole(UserRole.STANDART)
                .build();

        User userCreated = this.userRepository.save(user);

        log.info("usuairo {} foi salvo no banco de dados.", userCreated.getId());

        return new UserResponse(userCreated.getId(), userCreated.getName(), userCreated.getEmail());
    }

    /**
     * Método responsável pela logica de atualizar um usuario no banco de dados
     * 
     * @param id      recebe um UUID
     * @param userResponse recebe um objeto do tipo UserResponse
     * @return retorna um objeto do tipo UserResponse
     */
    @Transactional
    public UserResponse updataUser(UUID id, UserResponse userResponse) {
        User user = this.findUser(id);

        user.setName(userResponse.name());
        user.setEmail(userResponse.email());

        User userUpdated = this.userRepository.save(user);

        log.info("usuairo {} foi atualizado no banco de dados.", userUpdated.getId());

        return new UserResponse(userUpdated.getId(), userUpdated.getName(), userUpdated.getEmail());
    }

    /**
     * Método responsável pela logica de exclusão de um usuario do banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteUser(UUID id) {
        User user = this.findUser(id);

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
