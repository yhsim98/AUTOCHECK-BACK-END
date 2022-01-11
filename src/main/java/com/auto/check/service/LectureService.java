package com.auto.check.service;

import com.auto.check.domain.Attendance;
import com.auto.check.domain.Lecture;

import java.util.List;

public interface LectureService {
    List<Lecture> getLectureList();
    List<Attendance> getLectureStudent(Long lectureId);
    void singUpClass(Long lectureId);
}
