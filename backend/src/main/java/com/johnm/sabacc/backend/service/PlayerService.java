package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.SecurityUser;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.dto.UserSignupDTO;
import com.johnm.sabacc.backend.dto.UserTokenDTO;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import com.johnm.sabacc.backend.repository.GameHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private JpaUserDetailsService jpaUserDetailsService;
    private UserTokenService userTokenService;

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public PlayerService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder,
                         JpaUserDetailsService jpaUserDetailsService, UserTokenService userTokenService) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.userTokenService = userTokenService;
    }

    public List<Person> getAll() { return playerRepository.findAll(); }

    public List<Person> getByUsernames(List<String> usernames) {
        return playerRepository.findByNameIn(usernames);
    }

    public Person getByUsername(String username) {
        return playerRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No person with name '" + username + "'"));
    }

    public Person createPlayer(Person player) {
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        return playerRepository.save(player);
    }

    public UserTokenDTO signupNewUser(UserSignupDTO userSignupDTO) {
        Person newUser = new Person(
                userSignupDTO.getUsername(),
                passwordEncoder.encode(userSignupDTO.getPassword()),
                ROLE_USER
        );
        playerRepository.save(newUser);
        SecurityUser securityUser = (SecurityUser) jpaUserDetailsService.loadUserByUsername(newUser.getUsername());
        return userTokenService.generateToken(securityUser.getAuthorities(), newUser.getUsername());
    }

    public Person updatePlayer(Person player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(String username) {
        playerRepository.deleteById(username);
    }
}
