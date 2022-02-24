package com.auto.check.service;

import com.auto.check.api.dto.StudentAttendanceResponse;
import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.attendance.AttendanceRepository;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    public void alterStudentAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                        .orElseThrow(() -> new NonCriticalException(ErrorMessage.UNDEFINED_EXCEPTION));

        attendance.updateIsAttend();
    }

    public void singUpClass(Long lectureId){
        User user = userService.getLoginUserInfo();
        Lecture lecture = lectureService.getLectureById(lectureId);
        registrationService.singUpClass(user, lecture);

        for (int i = 1; i <= 14; i++) {
            for (var l : lecture.getLectureInfoList()) {
                Attendance attendance = Attendance.builder()
                        .user(user)
                        .week(i)
                        .isAttend((short) 0)
                        .lectureInfo(l).build();

                attendanceRepository.save(attendance);
            }
        }
    }

    public List<Attendance> getStudentLectureAllAttendance(Long lectureId) {
        User loginUser = userService.getLoginUserInfo();
        Lecture lecture = lectureService.getLectureById(lectureId);

        if(!registrationService.isRegistration(loginUser, lecture)){
            throw new NonCriticalException(ErrorMessage.STUDENT_NOT_REGISTRATION);
        }

        return attendanceRepository.findByUserAndLectureInfoIn(loginUser, lecture.getLectureInfoList());
    }

    public List<Attendance> getLectureStudentsAttendanceList(int week, Long lectureInfoId){
        LectureInfo lectureInfo = lectureService.getLectureInfoById(lectureInfoId);
        return attendanceRepository.findAttendanceByLectureInfoAndWeek(lectureInfo, week);
    }
}
