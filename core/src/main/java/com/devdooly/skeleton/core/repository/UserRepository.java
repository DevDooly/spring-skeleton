package com.devdooly.skeleton.core.repository;

import com.devdooly.skeleton.core.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT * FROM USER WHERE id = :id")
    User findUserById(@Param("id") Long id);

}
