package com.auto.check.domain.lecture;

import com.auto.check.domain.user.User;

import java.util.List;

public interface LectureRepositoryCustom {
    List<Lecture> findUserLectureList(Long userId);
    List<Lecture> findLectureByProfessor(User user);
}
