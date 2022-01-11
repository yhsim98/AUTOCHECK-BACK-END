package com.auto.check.mapper;

import com.auto.check.domain.Lecture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureMapper {
    List<Lecture> getLectureInfoList(Long userId);
    void insertRegistration(Long userId, Long lectureId);
    List<Lecture> getLectureInfoListByLectureId(Long lectureId);
    int getRegistrationNumByUserIdAndLectureId(Long userId, Long lectureId);
}
