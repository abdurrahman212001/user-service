package com.agilysys.user_service.repository;

import com.agilysys.user_service.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,Integer> {

}
