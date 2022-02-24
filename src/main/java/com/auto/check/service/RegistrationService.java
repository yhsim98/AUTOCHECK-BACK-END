package com.auto.check.service;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.registration.Registration;
import com.auto.check.domain.registration.RegistrationRepository;
import com.auto.check.domain.user.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final LectureService lectureService;

    public boolean isRegistration(User user, Lecture lecture){
        if(registrationRepository.countByStudentIdAndLectureId(user.getId(), lecture.getId()) < 1)
            return false;
        else
            return true;
    }

    @Transactional
    public Registration singUpClass(User user, Lecture lecture) {

        if(isRegistration(user, lecture)){
            throw new NonCriticalException(ErrorMessage.ALREADY_REGISTRATION);
        }

        return registrationRepository.save(Registration.builder()
                .lecture(lecture)
                .student(user)
                .build()
        );
    }
}
