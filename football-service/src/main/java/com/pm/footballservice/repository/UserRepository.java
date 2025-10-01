package com.pm.footballservice.repository;

import com.pm.footballservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public boolean existsByUsername(String username);
    public User findByUsername(String username);
}
