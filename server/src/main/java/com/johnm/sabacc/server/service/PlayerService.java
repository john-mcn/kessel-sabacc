package com.johnm.sabacc.server.service;

import com.johnm.sabacc.server.config.Authorities;
import com.johnm.sabacc.server.domain.SecurityUser;
import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.dto.UserSignupDTO;
import com.johnm.sabacc.server.dto.UserTokenDTO;
import com.johnm.sabacc.server.exceptions.AccessForbiddenException;
import com.johnm.sabacc.server.exceptions.EntityNotFoundException;
import com.johnm.sabacc.server.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
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

    public Person updatePlayer(String username, Person player, Authentication auth) {
        // Players can only update themselves (and admins can update anyone)
        Person authedPlayer = getByUsername(auth.getName()); //TODO unnecessary, just use auth?
        if (!authedPlayer.getUsername().equals(player.getUsername())
                && !authedPlayer.getRole().equals(Authorities.ROLE_ADMIN)) {
            throw new AccessForbiddenException("User '" + authedPlayer.getUsername()
                    + "' is not authorized to update user '" + player.getUsername() + "'");
        }

        Person storedPlayer = getByUsername(username);

        if (player.getUsername() != null) { storedPlayer.setUsername(player.getUsername()); }
        if (player.getPassword() != null) { storedPlayer.setPassword(passwordEncoder.encode(player.getPassword())); }
        if (player.getName() != null) { storedPlayer.setName(player.getName()); }
        storedPlayer.setCredits(player.getCredits());
        if (player.getTokens() != null) { storedPlayer.setTokens(player.getTokens()); }

        return playerRepository.save(storedPlayer);
    }

    public void deletePlayer(String username) {
        // Users can only delete themselves, admins can delete anyone
        playerRepository.deleteById(username);
    }
}
