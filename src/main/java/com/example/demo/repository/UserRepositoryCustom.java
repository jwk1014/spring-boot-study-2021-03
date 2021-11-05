package com.example.demo.repository;

import com.example.demo.model.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByEmailLike(String emailQuery);
}
