package com.auto.check.domain.attendance;

import java.util.List;

public interface AttendanceRepositoryCustom {
    Attendance findAttendanceById(Long id);
    List<Attendance> findAttendancesByUserIdAndWeek(int week, Long lectureInfoId);
}
