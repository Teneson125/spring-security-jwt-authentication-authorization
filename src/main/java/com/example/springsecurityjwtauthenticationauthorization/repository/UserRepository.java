package com.example.springsecurityjwtauthenticationauthorization.repository;

import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
