package com.auto.check.serviceImpl;

import com.auto.check.domain.Attendance;
import com.auto.check.domain.Lecture;
import com.auto.check.domain.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.mapper.AttendanceMapper;
import com.auto.check.mapper.LectureMapper;
import com.auto.check.service.LectureService;
import com.auto.check.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureMapper lectureMapper;
    private final AttendanceMapper attendanceMapper;
    private final UserService userService;

    @Override
    public List<Lecture> getLectureList() {
        User user = userService.getLoginUserInfo();

        return lectureMapper.getLectureInfoList(user.getId());
    }

    @Override
    public List<Attendance> getLectureStudent(Long lectureId) {
        User loginUser = userService.getLoginUserInfo();

        if(lectureMapper.getRegistrationNumByUserIdAndLectureId(loginUser.getId(), lectureId) < 1){
            throw new NonCriticalException(ErrorMessage.STUDENT_NOT_REGISTRATION);
        }

        return attendanceMapper.getStudentLectureAttendanceList(lectureId, loginUser.getId());
    }

    @Override
    public void singUpClass(Long lectureId){
        Long id = userService.getLoginUserInfo().getId();

        if(lectureMapper.getRegistrationNumByUserIdAndLectureId(id, lectureId) > 0){
            throw new NonCriticalException(ErrorMessage.STUDENT_ALREADY_REGISTRATION);
        }

        lectureMapper.insertRegistration(id, lectureId);

        List<Lecture> lectureInfoList = lectureMapper.getLectureInfoListByLectureId(lectureId);

        for(int i = 1; i <= 14; i++) {
            for (var l : lectureInfoList) {
                Attendance attendance = new Attendance();
                attendance.setUser_id(id);
                attendance.setWeek(i);
                attendance.setIs_attend((short) 0);
                attendance.setLecture_info_id(l.getLecture_info_id());
                attendanceMapper.insertAttendance(attendance);
            }
        }
    }
}
