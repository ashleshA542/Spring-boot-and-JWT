package com.jwt.project.repository;

import com.jwt.project.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,String> {


    Optional<User> findByUserName(String userName);

}
