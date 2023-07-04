package com.epam.esm.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.security.exception.JwtAuthorizationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration}")
    private long validityInMinutes;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtTokenProvider(@Qualifier("securityUserServiceImpl") UserDetailsService securityUserService) {
        this.userDetailsService = securityUserService;
    }

    @PostConstruct
    protected void init() {

        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);

            KeyPair kp = keyGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

            algorithm = Algorithm.RSA256(publicKey, privateKey);

            verifier = JWT
                    .require(algorithm)
                    .withIssuer(issuer)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String createToken(String username, String role) {
        Date expiresAt = Date.from(LocalDateTime.now()
                .plusMinutes(validityInMinutes)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        return JWT.create()
                .withClaim("username", username)
                .withClaim("role", role)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        DecodedJWT decodedJWT;

        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new JwtAuthorizationException(ex.getMessage());
        }

        if (decodedJWT.getExpiresAt().before(new Date()))
            throw new JwtAuthorizationException("JWT token is expired or invalid");

        return decodedJWT;
    }

    public Authentication getAuthentication(String token) {
        String username = JWT.decode(token)
                .getClaim("username")
                .asString();

        if (username == null) {
            throw new JwtAuthorizationException("JWT token has not username claim");
        }

        UserDetails userDetails;

        try {
            userDetails = this.userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (UsernameNotFoundException ex) {
            throw new JwtAuthorizationException("No corresponding username in database");
        }
    }
}
