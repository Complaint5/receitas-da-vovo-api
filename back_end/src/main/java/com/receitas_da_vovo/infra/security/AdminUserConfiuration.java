package com.receitas_da_vovo.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.receitas_da_vovo.domain.user.User;
import com.receitas_da_vovo.domain.user.UserRole;
import com.receitas_da_vovo.repositories.UserRepository;

/**
 * Classe responsável por cadastrar usuario admin no banco de dados
 */
@Configuration
public class AdminUserConfiuration implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${admin.name}")
    private String name;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;

    /**
     * Método responsável pela logica de cadastrar usuario admin no bando de dados
     */
    @Override
    public void run(String... args) throws Exception {
        User userAdmin = User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.passwordEncoder.encode(this.password))
                .userRole(UserRole.ADMIN)
                .activated(true)
                .build();

        if (this.userRepository.findUserByEmailNativeQuery(userAdmin.getEmail()).isEmpty()) {
            this.userRepository.save(userAdmin);
        }
    }
}
