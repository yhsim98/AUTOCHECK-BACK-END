package com.auto.check.domain.lecture;

import com.auto.check.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {
    List<Lecture> findLectureByProfessor(User user);
    List<Lecture> findUserLectureList(Long userId);
}
