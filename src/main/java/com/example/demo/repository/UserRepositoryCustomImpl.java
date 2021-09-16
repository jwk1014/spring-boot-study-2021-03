package com.example.demo.repository;

import com.example.demo.model.entity.QUser;
import com.example.demo.model.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QUser user = QUser.user;

    @Override
    public List<User> findByEmailLike(String emailQuery) {
        return queryFactory.select(user)
                .from(user)
                .where(user.email.like("%"+emailQuery+"%"))
                .orderBy(user.email.asc())
                .fetch();
    }
}
