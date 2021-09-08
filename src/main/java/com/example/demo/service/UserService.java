package com.example.demo.service;

import com.example.demo.httpException.ResponseError;
import com.example.demo.model.entity.User;
import com.example.demo.model.req.RUserSign;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public Authentication signIn(final RUserSign.In req) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
    }

    public void signUp(final RUserSign.Up req) {

        final User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            Optional.ofNullable(e.getCause())
                    .filter(cause -> cause instanceof ConstraintViolationException)
                    .map(cause -> (ConstraintViolationException) cause)
                    .map(ConstraintViolationException::getSQLException)
                    .map(SQLException::getErrorCode)
                    .filter(code -> code.equals(1062))
                    .ifPresent(code -> {
                        throw ResponseError.BadRequest.ALREADY_EXISTS_EMAIL.getResponseException();
                    });

            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findByEmail(username);

        if(user == null) {
            return null;
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
