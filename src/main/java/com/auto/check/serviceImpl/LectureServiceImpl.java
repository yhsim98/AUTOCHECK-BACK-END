package com.auto.check.serviceImpl;

import com.auto.check.domain.Attendance;
import com.auto.check.domain.Lecture;
import com.auto.check.domain.User;
import com.auto.check.mapper.AttendanceMapper;
import com.auto.check.mapper.LectureMapper;
import com.auto.check.service.LectureService;
import com.auto.check.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        System.out.println(lectureId);
        return attendanceMapper.getStudentLectureAttendanceList(lectureId, userService.getLoginUserInfo().getId());
    }
}
