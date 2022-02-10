package com.auto.check.domain.lecture;

import java.util.List;

public interface LectureRepositoryCustom {
    List<Lecture> findUserLectureList(Long userId);
}
