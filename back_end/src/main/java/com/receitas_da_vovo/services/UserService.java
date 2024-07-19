package com.receitas_da_vovo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.dtos.UserDto;
import com.receitas_da_vovo.entities.UserEntity;
import com.receitas_da_vovo.enums.UserRole;
import com.receitas_da_vovo.repositories.UserRepository;
import com.receitas_da_vovo.utils.ClassConverter;

import jakarta.transaction.Transactional;

/**
 * Classe responsálve pela logica relacionada a UserEntity
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassConverter classConverter;

    /**
     * Método responsável pela logica de salvar um usuario no banco de dados
     * 
     * @param userDto recebe um objeto do tipo UserDto
     * @return retorna um objeto do tipo UserDto
     */
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        UserEntity user = UserEntity.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(userDto.password())
                .activated(true)
                .userRole(UserRole.STANDART)
                .build();

        UserEntity userCreated = this.userRepository.save(user);

        return this.classConverter.userEntityToUserDto(userCreated);
    }

    /**
     * Método responsável pela logica de atualizar um usuario no banco de dados
     * 
     * @param id      recebe um UUID
     * @param userDto recebe um objeto do tipo UserDto
     * @return retorna um objeto do tipo UserDto
     */
    @Transactional
    public UserDto updataUser(UUID id, UserDto userDto) {
        UserEntity user = this.findUserEntity(id);

        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        return this.classConverter.userEntityToUserDto(this.userRepository.save(user));
    }

    /**
     * Método responsável pela logica de exclusão de um usuario do banco de dados
     * 
     * @param id recebe um UUID
     * @return retorna um boolean
     */
    @Transactional
    public boolean deleteUser(UUID id) {
        UserEntity user = this.findUserEntity(id);

        user.setActivated(false);

        this.userRepository.save(user);

        return true;
    }

    /**
     * Método responsável pela logica de buscar um usuario no banco de dados pelo id
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo UserDto
     */
    public UserDto findUserById(UUID id) {
        return this.findUserById(id);
    }

    /**
     * Método responsável pela logica de buscar todos os usuario no banco de dados
     * 
     * @return retorna uma lista de objetos do tipo UserDto
     */
    public List<UserDto> findAllUsers() {
        return this.userRepository.findAllUsersByActivatedTrue().stream()
                .map(user -> this.classConverter.userEntityToUserDto(user))
                .toList();
    }

    /**
     * Método privado responsável pela logica de buscar um usuario no banco de dados
     * caso o coontrario lançar uma exeção
     * 
     * @param id recebe um UUID
     * @return retorna um objeto do tipo UserEntity
     * @throws exception Lançara uma exeção caso não encontre o usuarip
     *                   /////////////////////////////////////////////
     */
    private UserEntity findUserEntity(UUID id) throws RuntimeException {
        Optional<UserEntity> user = this.userRepository.findUserByIdAndActivatedTrue(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("Usuario não encontrado.");
        }
    }
}
