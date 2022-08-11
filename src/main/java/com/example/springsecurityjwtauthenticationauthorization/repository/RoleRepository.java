package com.example.springsecurityjwtauthenticationauthorization.repository;

import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
