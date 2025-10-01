package com.pm.footballservice.repository;

import com.pm.footballservice.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club,Integer> {
    public boolean existsById(Integer clubId);
}
