package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.SecurityUser;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    public JpaUserDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return playerRepository
                .findById(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }
}
