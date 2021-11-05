package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositorySupport {
    List<Post> findByTitleLike(String titleQuery);

    Page<Post> findByTitleLikePageable(String titleQuery, Pageable pageable);
}
