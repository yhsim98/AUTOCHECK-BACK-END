package com.auto.check.domain.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {
    List<Lecture> findLectureByProfessor(Long userId);
}
