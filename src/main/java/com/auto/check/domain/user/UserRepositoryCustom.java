package com.auto.check.domain.user;

import com.auto.check.domain.lecture.Lecture;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findLectureRelatedUsers(Lecture lecture);
}
