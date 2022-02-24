package com.auto.check.domain.attendance;

import com.auto.check.domain.lectureinfo.LectureInfo;

import java.util.List;

public interface AttendanceRepositoryCustom {
    Attendance findAttendanceById(Long id);
    List<Attendance> findAttendanceByLectureInfoAndWeek(LectureInfo li, int week);
}
