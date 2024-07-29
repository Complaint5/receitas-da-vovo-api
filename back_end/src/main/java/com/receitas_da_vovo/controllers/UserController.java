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

import com.receitas_da_vovo.dtos.SaveUserDto;
import com.receitas_da_vovo.dtos.UserDto;
import com.receitas_da_vovo.services.UserService;

import jakarta.validation.Valid;

/**
 * Classe reponsável pelos endpoints para usuario
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Método responsável pelo endpoint de salvar o usuario
     * 
     * @param saveUserDto recebe um objeto do tipo SaveUserDto
     * @return retorna um ResponseEntity do tipo UserDto com o estatos created
     */
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid SaveUserDto saveUserDto) {
        UserDto user = this.userService.saveUser(saveUserDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    /**
     * Método responsável pelo endpoint de atualizar o usuario
     * 
     * @param id      recebe um UUID
     * @param userDto recebe um objeto do tipo UserDto
     * @return retorna um ResponseEntity do tipo UserDto com o estatos ok
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(this.userService.updataUser(id, userDto));
    }

    /**
     * Método responsável pelo endpoint de deletar o usuario
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity com o estatos no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método responsável pelo endpoint de buscar o usuario pelo id
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity do tipo UserDto com o estatos ok
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.userService.findUserById(id));
    }

    /**
     * Método responsável pelo endpoint de buscar todos os usuario
     * 
     * @return retorna um ResponseEntity com uma lista do tipo UserDto com o estatos
     *         ok
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(this.userService.findAllUsers());
    }
}
