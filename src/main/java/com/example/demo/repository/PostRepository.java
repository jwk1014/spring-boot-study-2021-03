package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitle(String title);

    /*
    SELECT *
    FROM post
    WHERE title like '%테스트%'
    AND id > 1
    ORDER BY id DESC
     */
    List<Post> findByTitleLikeAndIdGreaterThanOrderByIdDesc(String title, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Post SET title = :title WHERE id >= :minId")
    int updateTitle(
            @Param("minId") Long minId,
            @Param("title") String title
    );
}
