package com.github.abeatrizsc.user_service.security;

import com.github.abeatrizsc.user_service.exceptions.UserNotFoundException;
import com.github.abeatrizsc.user_service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return repository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDetails loadUserById(String id) throws UserNotFoundException {
        return repository.findById(id)
                .map(CustomUserDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }
}