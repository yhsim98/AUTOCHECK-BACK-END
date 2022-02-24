package com.auto.check.domain.attendance;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepositoryCustom{
    List<Attendance> findAttendanceByLectureInfoAndWeek(LectureInfo li, int week);

    @Modifying(clearAutomatically = true)
    @Query("update Attendance a set a.isAttend=1 where a.user.id=:id")
    int bulkStudentAttendance(@Param("id") Long id);

    List<Attendance> findByUserAndLectureInfoIn(User user, List<LectureInfo> lectureInfoList);
}
