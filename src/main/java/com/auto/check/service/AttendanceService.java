package com.auto.check.service;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.attendance.AttendanceRepository;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.web.dto.StudentAttendanceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    @PersistenceContext
    private final EntityManager em;

    public void alterStudentAttendance(Long attendanceId) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                        .orElseThrow(() -> new NonCriticalException(ErrorMessage.UNDEFINED_EXCEPTION));

        attendance.updateIsAttend();
    }

    public List getStudentsAttendanceList(int week, Long lectureInfoId){

        return attendanceRepository.findAttendancesByUserId(week, lectureInfoId)
                .stream()
                .toArray();
    }
}
