package com.sambat.demo.Repository;

import com.sambat.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM User u WHERE :name IS NULL OR LOWER(u.name) LIKE %LOWER(:name)%)")
    UserEntity findByUserName(@Param("name") String name);
}
