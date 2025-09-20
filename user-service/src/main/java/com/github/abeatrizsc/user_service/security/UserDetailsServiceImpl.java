package com.github.abeatrizsc.user_service.security;

import com.github.abeatrizsc.user_service.domain.User;
import com.github.abeatrizsc.user_service.exceptions.AuthException;
import com.github.abeatrizsc.user_service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new AuthException();
        }

        return new CustomUserDetails(user.get());
    }

    public UserDetails loadUserById(String id) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new AuthException();
        }

        return new CustomUserDetails(user.get());
    }
}