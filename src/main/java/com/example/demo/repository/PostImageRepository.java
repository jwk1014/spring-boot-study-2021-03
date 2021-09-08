package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostOrderById(Post post);
}