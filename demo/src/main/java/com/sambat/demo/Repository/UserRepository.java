package com.sambat.demo.Repository;

import com.sambat.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE :name IS NULL OR LOWER(u.name) LIKE %:name%")
    List<UserEntity> findByUserName(@Param("name") String name);

    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
}
