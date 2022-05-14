package com.auto.check.domain.lectureinfo;

import com.auto.check.domain.value.LectureTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LectureInfoRepository extends JpaRepository<LectureInfo, Long>, LectureInfoCustom {
    List<LectureInfo> findLectureInfoByLecture(Long lectureId);

}
