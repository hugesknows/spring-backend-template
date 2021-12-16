package com.template.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddress(String emailAddress);
}
