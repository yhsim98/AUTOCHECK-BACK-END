package com.auto.check.domain.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);
    int countByAccount(String account);
    @Query("SELECT count(u.school_number) FROM User u WHERE u.school_number=:schoolNum")
    int countBySchool_number(@Param("schoolNum") String schoolNum);
}
