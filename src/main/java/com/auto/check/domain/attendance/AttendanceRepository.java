package com.auto.check.domain.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> getAttendanceByWeekAndLectureInfoId(int week, Long lectureInfoId);
}
