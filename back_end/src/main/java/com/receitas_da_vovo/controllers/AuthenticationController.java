package com.receitas_da_vovo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.receitas_da_vovo.domain.authentication.AuthenticationRequest;
import com.receitas_da_vovo.domain.authentication.AuthenticationResponse;
import com.receitas_da_vovo.services.AuthenticationService;

import jakarta.validation.Valid;

/**
 * Classe reponsável pelos endpoints para autenticação
 */
@RestController
@RequestMapping("/v1/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Método responsável pelo endpoint de autenticar usuario
     * 
     * @param authenticationRequest recebe um objeto do tipo AuthenticationRequest
     * @return retorna um ResponseEntity do tipo AuthenticationResponse com o status
     *         ok
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(this.authenticationService.login(authenticationRequest));
    }

    // @PostMapping("/refreshtoken")
    // public ResponseEntity<AuthenticationResponse> refreshToken (){
    // JwtAuthenticationToken token
    // return null;
    // }
}
