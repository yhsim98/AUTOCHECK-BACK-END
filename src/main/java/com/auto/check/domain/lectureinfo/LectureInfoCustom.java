package com.auto.check.domain.lectureinfo;

import com.auto.check.domain.value.LectureTime;

import java.util.List;

public interface LectureInfoCustom {
    List<LectureInfo> findLectureInfoByLectureTime(LectureTime lectureTime);
}
