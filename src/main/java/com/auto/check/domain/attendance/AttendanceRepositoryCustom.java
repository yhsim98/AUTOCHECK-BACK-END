package com.auto.check.domain.attendance;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface AttendanceRepositoryCustom {
    Attendance findAttendanceById(Long id);
    List<Attendance> findAttendanceByLectureInfoAndWeek(LectureInfo li, int week);
    @Modifying(clearAutomatically = true)
    void updateAttendancesByLectureInfoAndWeek(List<User> students, LectureInfo lectureInfo, int week);
}
