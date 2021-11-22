package com.auto.check.mapper;

import com.auto.check.domain.Attendance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceMapper {
    void alterStudentAttendance();
    List<Attendance> getStudentLectureAttendanceList(Long lectureId, Long userId);
}
