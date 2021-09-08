package com.example.demo.controller;

import com.example.demo.httpException.ResponseError;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.model.req.RPost;
import com.example.demo.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    // TODO FIX
    private final User signedUser = User.builder().id(1L).build();

    // [GET] /api/v1/posts
    // Post 리스트 조회
    // @RequestMapping(method = RequestMethod.GET, value = "/posts")
    @ApiOperation("Post 리스트 조회")
    @GetMapping("/posts")
    public List<RPost.ListGetRes> getPostList(
            @RequestParam(value = "query", required = false) String query
    ) {
        log.info("query : " + query);

        return postService.getPostList()
                .stream()
                .map(post -> RPost.ListGetRes.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .user(RPost.GetUserRes.builder()
                                .id(post.getUser().getId())
                                .name(post.getUser().getName())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    // [GET] /api/v1/posts/{id}
    // Post 1개 상세 조회
    @GetMapping("/posts/{id}")
    public RPost.GetRes getPost(
            @PathVariable Long id
    ) {
       final Post post = postService.getPost(id);
       final RPost.GetRes res = RPost.GetRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(RPost.GetUserRes.builder()
                        .id(post.getUser().getId())
                        .name(post.getUser().getName())
                        .build())
                .build();

       return res;
       // return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // [POST] /api/v1/posts
    // Post 생성
    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(
            @RequestBody @Valid RPost.CreationReq req
    ) {
        req.setUser(signedUser);

        postService.createPost(req);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // [PUT] /api/v1/posts
    // Post 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id,
            @RequestBody RPost.ModificationReq req
    ) {
        req.setId(id);
        req.setUser(signedUser);

        postService.updatePost(req);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // [DELETE] /api/v1/posts
    // Post 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id, signedUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/posts/{id}/images")
    public ResponseEntity<Void> savePostImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        postService.savePostImage(id, signedUser, file);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
