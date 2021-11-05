package com.example.demo.mongoRepository;

import com.example.demo.model.mongoEntity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoUserRepository extends MongoRepository<User, String> {

    List<User> findUserByName(String name);
}