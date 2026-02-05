package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.dto.UserTokenDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserTokenService {

    private final JwtEncoder jwtEncoder;

    public UserTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public UserTokenDTO generateToken(Collection<? extends GrantedAuthority> authorities, String username) {
        Instant now = Instant.now();
        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(username)
                .claim("scope", scope)
                .build();
        return new UserTokenDTO(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }
}