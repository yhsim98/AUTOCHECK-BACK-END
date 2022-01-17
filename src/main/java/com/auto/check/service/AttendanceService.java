package com.auto.check.service;

import com.auto.check.domain.Attendance;
import com.auto.check.util.EntityManagerFactoryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    @PersistenceContext
    EntityManager em;

    public void alterStudentAttendance(Long attendanceId) {

        Attendance attendance = em.find(Attendance.class, attendanceId);

        attendance.updateIsAttend();

    }

    public List<Attendance> getStudentsAttendanceList(int week, Long lectureInfoId){

        return em.createQuery("SELECT a FROM Attendance a WHERE a.week=:week AND a.lectureInfo=:id")
                .setParameter("week", week)
                .setParameter("id", lectureInfoId)
                .getResultList();
    }
}
