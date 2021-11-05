package com.example.demo.service;

import com.example.demo.aop.TestPoint;
import com.example.demo.httpException.ResponseError;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.PostImage;
import com.example.demo.model.entity.User;
import com.example.demo.model.req.PageRes;
import com.example.demo.model.req.RPost;
import com.example.demo.repository.PostImageRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    @Value("${upload.rootPath}")
    private String uploadRootPath;

    public PageRes<RPost.ListGetRes> getPostList(final String titleQuery, final Integer page, final Integer size) {
        final Pageable pageable = PageRequest.of(page - 1, size);

        final Page<Post> postPage = postRepository.findByTitleLikePageable(titleQuery, pageable);

        final List<RPost.ListGetRes> list = postPage.getContent()
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

        return PageRes.<RPost.ListGetRes>builder()
                .list(list)
                .page(page)
                .size(size)
                .lastPage(postPage.getTotalPages())
                .build();
    }

    @TestPoint
    public Post getPost(final Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> ResponseError.NotFound.POST_NOT_EXISTS.getResponseException(id.toString()));
    }

    @Transactional
    public void createPost(final RPost.CreationReq req) {
        final Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .user(req.getUser())
                .build();

        postRepository.save(post);

        //MDC.put("POST_ID", post.getId().to);

        throw new RuntimeException("test");
    }

    public void updatePost(final RPost.ModificationReq req) {
        final Post post = postRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("post "+req.getId()+" not exists"));

        if(!post.getUser().getId().equals(req.getUser().getId())) {
            throw new RuntimeException("post "+req.getId()+" unauthorized modification");
        }

        if(req.getTitle() != null) {
            post.setTitle(req.getTitle());
        }

        if(req.getContent() != null) {
            post.setContent(req.getContent());
        }

        postRepository.save(post);
    }

    public void deletePost(final Long id, final User signedUser) {
        final Post post =  postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post "+id+" not exists"));

        if(!post.getUser().getId().equals(signedUser.getId())) {
            throw new RuntimeException("post "+id+" unauthorized deletion");
        }

        postRepository.delete(post);
    }

    public void savePostImage(final Long id, final User signedUser, final MultipartFile file) {
        final Post post =  postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post "+id+" not exists"));

        if(!post.getUser().getId().equals(signedUser.getId())) {
            throw new RuntimeException("post "+id+" unauthorized deletion");
        }

        final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        final String date = String.format("%04d%02d%02d", zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth());

        final Tika tika = new Tika();
        String mimeTypeString = null;
        try {
            mimeTypeString = tika.detect(file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("invalid file type");
        }

        if(!Set.of(
                MediaType.IMAGE_PNG_VALUE,
                MediaType.IMAGE_JPEG_VALUE).contains(mimeTypeString)) {
            throw new RuntimeException("invalid file type");
        }

        final String extension = mimeTypeString.substring(mimeTypeString.lastIndexOf('/')+1);

        final String fileName = UUID.randomUUID().toString() + "-" + zonedDateTime.getNano() + "." + extension;

        final String folderPath = "/" + date;

        final File folder = new File(uploadRootPath + folderPath);

        if(!folder.exists()) {
            folder.mkdir();
        }

        final String path = "/" + date + "/" + fileName;

        try {
            file.transferTo(new File(uploadRootPath + path));
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }

        postImageRepository.save(PostImage.builder()
                .post(post)
                .path(path)
                .build());
    }
}
