package com.example.demo.repository;

import com.example.demo.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertUser() {
        final User user = User.builder()
                .name("name")
                .email("test@test.com")
                .password(passwordEncoder.encode("test"))
                .build();

        userRepository.save(user);
    }

    @Test
    public void findByEmailLike() {
        final List<User> userList = userRepository.findByEmailLike("test.com");

        for(User user : userList) {
            System.out.println(user.getName() + " / " + user.getEmail());
        }
    }
}
