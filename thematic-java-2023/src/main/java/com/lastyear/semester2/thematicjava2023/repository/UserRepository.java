package com.lastyear.semester2.thematicjava2023.repository;

import com.lastyear.semester2.thematicjava2023.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
