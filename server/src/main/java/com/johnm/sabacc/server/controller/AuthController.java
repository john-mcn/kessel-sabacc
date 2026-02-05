package com.johnm.sabacc.server.controller;

import com.johnm.sabacc.server.dto.UserSignupDTO;
import com.johnm.sabacc.server.dto.UserTokenDTO;
import com.johnm.sabacc.server.service.PlayerService;
import com.johnm.sabacc.server.service.UserTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PlayerService playerService;
    private final UserTokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(PlayerService playerService, UserTokenService tokenService,  AuthenticationManager authenticationManager) {
        this.playerService = playerService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserTokenDTO> signup(@RequestBody UserSignupDTO userSignupDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playerService.signupNewUser(userSignupDTO));
    }

    // @PostMapping("/login")
    // public ResponseEntity<UserTokenDTO> login(Authentication authentication) {
    //     UserTokenDTO tokenDTO = tokenService.generateToken(authentication.getAuthorities(), authentication.getName());
    //     return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserSignupDTO userSignInDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignInDTO.getUsername(),
                        userSignInDTO.getPassword()
                )
        );

        UserTokenDTO tokenDTO = tokenService.generateToken(authentication.getAuthorities(), authentication.getName());

        return ResponseEntity.ok(
                Map.of(
                        "token", tokenDTO.getToken(),
                        "user", playerService.getByUsername(authentication.getName())
                )
        );
    }
}
