package com.receitas_da_vovo.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Classe responsável por buscar usuario no banco de dados com base no email
 */
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Método responsável pela logica de buscar usuario no banco de dados com base
     * no email
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByEmail(username);
    }
}
