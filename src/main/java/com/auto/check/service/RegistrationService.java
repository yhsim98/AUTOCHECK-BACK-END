package com.auto.check.service;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.registration.RegistrationRepository;
import com.auto.check.domain.user.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final AttendanceService attendanceService;
    private final UserService userService;
    private final LectureService lectureService;

    public boolean isRegistration(User user, Lecture lecture){
        if(registrationRepository.countByStudentIdAndLectureId(user.getId(), lecture.getId()) < 1)
            return false;
        else
            return true;
    }

    public void singUpClass(Long lectureId) {
        User user = userService.getLoginUserInfo();
        Lecture lecture = lectureService.getLectureById(lectureId);

        if(isRegistration(user, lecture)){
            throw new NonCriticalException(ErrorMessage.ALREADY_REGISTRATION);
        }

        attendanceService.createStudentAttendanceInfo(user, lecture);
    }
}
