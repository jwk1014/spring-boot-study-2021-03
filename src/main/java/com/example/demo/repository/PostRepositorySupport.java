package com.example.demo.repository;

import com.example.demo.model.entity.Post;

import java.util.List;

public interface PostRepositorySupport {
    List<Post> findByTitleLike(String titleQuery);
}
