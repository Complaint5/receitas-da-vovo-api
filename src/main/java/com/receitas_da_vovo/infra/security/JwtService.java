package com.receitas_da_vovo.infra.security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.receitas_da_vovo.domain.authentication.AuthenticationResponse;
import com.receitas_da_vovo.domain.user.User;

/**
 * Classe responsável pela criação do token jwt
 */
@Service
public class JwtService {
    @Autowired
    private JwtEncoder jwtEncoder;

    private Instant now = Instant.now();
    @Value("${token.access-expiration}")
    private int expiry;

    /**
     * Método reponsável por gerar o token jwt
     * 
     * @param user recebe um objeto do tipo User
     * @return retorna um objeto do tipo AuthenticationResponse
     */
    public AuthenticationResponse generateToken(User user) {
        return new AuthenticationResponse(this.generateAccessTokenJwt(user), expirateTimeAccessToken());
    }

    /**
     * Método responsável pela logica de criar o token jwt
     * 
     * @param user recebe um objeto do tipo User
     * @return retorna uma String
     */
    private String generateAccessTokenJwt(User user) {
        String scopes = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("receitas-da-vovo-api")
                .issuedAt(this.now)
                .expiresAt(this.expirateTimeAccessToken())
                .subject(user.getId().toString())
                .claim("scope", scopes)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // private String generateRefreshTokenJwt(User user) {
    // JwtClaimsSet claims = JwtClaimsSet.builder()
    // .issuer("receitas-da-vovó-api")
    // .issuedAt(now)
    // .expiresAt(now.plusSeconds(expiry))
    // .subject(user.getId().toString())
    // .build();
    // return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    // }

    /**
     * Método responsável pelo logica de expiração do token jwt
     * 
     * @return
     */
    private Instant expirateTimeAccessToken() {
        return this.now.plusSeconds(this.expiry);
    }
}
