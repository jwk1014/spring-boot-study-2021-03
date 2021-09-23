package com.example.demo.repository;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.QPost;
import com.example.demo.model.entity.QUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.function.Function;

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

    @Override
    public Page<Post> findByTitleLikePageable(String titleQuery, Pageable pageable) {
        final JPQLQuery<Post> query = from(post)
                //.where(post.title.contains(titleQuery))
                .where(predicateOptional(post.title::contains, titleQuery));

        final long total = query.fetchCount();

        final List<Post> content = query
                .innerJoin(post.user, user).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    private <T> Predicate predicateOptional(final Function<T, Predicate> whereFunc, final T value) {
        return value != null ? whereFunc.apply(value) : null;
    }
}
