package com.receitas_da_vovo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.authentication.AuthenticationRequest;
import com.receitas_da_vovo.domain.authentication.AuthenticationResponse;
import com.receitas_da_vovo.domain.user.User;
import com.receitas_da_vovo.infra.exceptions.AuthenticationFailedException;
import com.receitas_da_vovo.infra.exceptions.UserNotFoundException;
import com.receitas_da_vovo.infra.security.JwtService;
import com.receitas_da_vovo.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService; // TODO: Criar canais para poder postar uma receita

    /**
     * Método resonsável pela lógica de autenticação do usuario
     * 
     * @param AuthenticationRequest recebe um objeto do tipo AuthenticationRequest
     * @return retorna um objeto do tipo AuthenticationResponse
     */
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        User user = this.userRepository.findUserByEmailNativeQuery(authenticationRequest.email())
                .orElseThrow(() -> new UserNotFoundException());
        if (this.isLoginCorrect(authenticationRequest.password(), user)) {
            log.info("Usuario {} foi autenticado.", user.getId());
            return this.jwtService.generateToken(user);
        }
        throw new AuthenticationFailedException();
    }

    // TODO: fazer metodo refreshToken()

    // public AuthenticationResponse refreshToken(AuthenticationRequest
    // authenticationRequest){
    // User user =
    // this.userRepository.findUserByEmailNativeQuery(authenticationRequest.email())
    // .orElseThrow(() -> new UserNotFoundException());
    // }

    /**
     * Método resonsável pela lógica de verificar senha do usuario
     * 
     * @param password recebe uma String
     * @param user     recebe um objeto do tipo User
     * @return retorna um boolean
     */
    private boolean isLoginCorrect(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
