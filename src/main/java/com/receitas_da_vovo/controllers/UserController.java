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

import com.receitas_da_vovo.domain.user.SaveUserResponse;
import com.receitas_da_vovo.domain.user.SaveUserRequest;
import com.receitas_da_vovo.domain.user.UpdateUserRequest;
import com.receitas_da_vovo.domain.user.UpdateUserResponse;
import com.receitas_da_vovo.domain.user.UserResponse;
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
     * @param SaveUserRequest recebe um objeto do tipo SaveUserRequest
     * @return retorna um ResponseEntity do tipo SaveUserResponse com o estatos
     *         created
     */
    @PostMapping
    public ResponseEntity<SaveUserResponse> saveUser(@RequestBody @Valid SaveUserRequest saveUserRequest) {
        SaveUserResponse user = this.userService.saveUser(saveUserRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    /**
     * Método responsável pelo endpoint de atualizar o usuario
     * 
     * @param id                     recebe um UUID
     * @param UpdateUserRequest      recebe um objeto do tipo UpdateUserRequest
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity do tipo UpdateUserRequest com o estatos ok
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable UUID id,
            @RequestBody @Valid UpdateUserRequest updateUserRequest, JwtAuthenticationToken token) {
        return ResponseEntity.ok(this.userService.updataUser(id, updateUserRequest, token));
    }

    /**
     * Método responsável pelo endpoint de deletar o usuario
     * 
     * @param id                     recebe um UUID
     * @param JwtAuthenticationToken recebe um objeto do tipo JwtAuthenticationToken
     * @return retorna um ResponseEntity com o estatos no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, JwtAuthenticationToken token) { // TODO: rever
        this.userService.deleteUser(id, token);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método responsável pelo endpoint de buscar o usuario pelo id
     * 
     * @param id recebe um UUID
     * @return retorna um ResponseEntity do tipo UserResponse com o estatos ok
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STANDART')")
    public ResponseEntity<UserResponse> findUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.userService.findUserById(id));
    }

    /**
     * Método responsável pelo endpoint de buscar todos os usuario
     * 
     * @return retorna um ResponseEntity com uma lista do tipo UserResponse com o
     *         estatos ok
     */
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok(this.userService.findAllUsers());
    }
}
