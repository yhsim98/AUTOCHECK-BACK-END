package com.auto.check.domain.lectureinfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureInfoRepository extends JpaRepository<LectureInfo, Long> {
    List<LectureInfo> findLectureInfoByLecture(Long lectureId);
}
