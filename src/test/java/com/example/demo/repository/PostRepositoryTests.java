package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void testFindById() {
        final Optional<Post> postOptional = postRepository.findById(1L);
        postOptional.ifPresent(post -> {
            System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
        });
    }

    @Test
    public void testFindByTitle() {
        final Optional<Post> postOptional = postRepository.findByTitle("테스트제목 2");
        postOptional.ifPresent(post -> {
            System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
        });
    }

    @Test
    public void testInsert() {
        final Post post = Post.builder()
                .title("test title")
                .content("test content")
                .user(User.builder().id(1L).build())
                .build();

        postRepository.save(post);

        Assertions.assertNotNull(post.getId());

        System.out.println("id : "+post.getId());
        System.out.println("title : "+post.getTitle());
        System.out.println("content : "+post.getContent());
        System.out.println("created_at : "+post.getCreatedAt());
        System.out.println("updated_at : "+post.getUpdatedAt());
    }

    @Test
    public void testUpdate() {
        final Optional<Post> postOptional = postRepository.findById(22L);

        Assertions.assertTrue(postOptional.isPresent());

        final Post post = postOptional.get();
        post.setContent(post.getContent() + " update");

        postRepository.save(post);

        System.out.println("id : "+post.getId());
        System.out.println("title : "+post.getTitle());
        System.out.println("content : "+post.getContent());
        System.out.println("created_at : "+post.getCreatedAt());
        System.out.println("updated_at : "+post.getUpdatedAt());
    }

    @Test
    public void testDelete() {
        final long originCount = postRepository.count();

        final Optional<Post> postOptional = postRepository.findAll()
                .stream().max(Comparator.comparing(Post::getId));

        Assertions.assertTrue(postOptional.isPresent());

        final Post post = postOptional.get();
        postRepository.delete(post);

        final long count = postRepository.count();

        Assertions.assertEquals(originCount - 1, count);
    }

    @Test
    public void testFindByTitleLikeAndIdGreaterThanOrderByIdDesc() {
        List<Post> postList = postRepository.findByTitleLikeAndIdGreaterThanOrderByIdDesc("%테스트%", 1L);

        System.out.println(postList);
    }

    // Test Transactional에서 update를 수행하면 함수 종료시 rollback됩니다.
    // rollback 전 데이터 확인을 위해 update후 find 조회를 하였습니다.
    @Transactional
    @Test
    public void testQueryUpdate() {
        int updateCount = postRepository.updateTitle(4L, "타이틀 테스트");

        System.out.println(updateCount);

        List<Post> postList = postRepository.findByTitleLikeAndIdGreaterThanOrderByIdDesc("%타이틀 테스트%", 3L);

        for(Post post : postList) {
            System.out.println("id "+post.getId()+" title "+post.getTitle());
        }
    }

    @Transactional
    @Test
    public void testFindById2() {
        final Optional<Post> postOptional = postRepository.findById(1L);
        postOptional.ifPresent(post -> {
            System.out.println("post : "
                    +post.getId()+"/"
                    +post.getTitle()+"/"
                    +post.getContent()+"/"
                    +post.getUser().getId()+"/"
                    +post.getUser().getName());
        });
    }

    @Transactional
    @Test
    public void findByTitleLike() {
        List<Post> postList = postRepository.findByTitleLike("test");

        for(Post post : postList) {
            System.out.println("id "+post.getId()+" title "+post.getTitle()+" / user name : "+post.getUser().getName());
        }
    }
}
