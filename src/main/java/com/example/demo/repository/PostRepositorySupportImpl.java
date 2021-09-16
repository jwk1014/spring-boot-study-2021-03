package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.QPost;
import com.example.demo.model.entity.QUser;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostRepositorySupportImpl extends QuerydslRepositorySupport implements PostRepositorySupport {
    private final QPost post = QPost.post;
    private final QUser user = QUser.user;

    public PostRepositorySupportImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findByTitleLike(String titleQuery) {
        return from(post)
                .innerJoin(post.user, user).fetchJoin()
                .where(post.title.like("%"+titleQuery+"%"))
                .orderBy(post.id.desc())
                .fetch();
    }
}
