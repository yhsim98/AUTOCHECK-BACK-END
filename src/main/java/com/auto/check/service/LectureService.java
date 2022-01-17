package com.auto.check.service;

import com.auto.check.domain.Attendance;
import com.auto.check.domain.Lecture;
import com.auto.check.domain.LectureInfo;
import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserType;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.mapper.AttendanceMapper;
import com.auto.check.mapper.LectureMapper;
import com.auto.check.util.EntityManagerFactoryUtil;
import com.auto.check.web.dto.LectureCreateRequestDTO;
import com.auto.check.web.dto.UserLectureInfoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureMapper lectureMapper;
    private final AttendanceMapper attendanceMapper;
    private final UserService userService;
    @PersistenceContext
    EntityManager em;

    public List<UserLectureInfoResponseDTO> getLectureList() {
        User user = userService.getLoginUserInfo();

        return em.createQuery("SELECT new com.auto.check.web.dto.UserLectureInfoResponseDTO(li.id, l.id, l.lectureName, li.lectureRoom.lecture_room, li.lectureTime)\n" +
                        "        FROM LectureInfo li\n" +
                        "        JOIN FETCH Lecture l\n ON l=li.lecture" +
                        "        WHERE l IN(SELECT r.lecture FROM Registration r WHERE r.student.id=" + user.getId().toString() + ")")
                .getResultList();
    }

    public List<LectureInfo> getLectureInfo(Long lectureId) {

        List<LectureInfo> lectureInfoList = em.find(Lecture.class, lectureId).getLectureInfoList();

        return lectureInfoList;
    }

    public List<Attendance> getLectureStudent(Long lectureId) {
        User loginUser = userService.getLoginUserInfo();

        if (lectureMapper.getRegistrationNumByUserIdAndLectureId(loginUser.getId(), lectureId) < 1) {
            throw new NonCriticalException(ErrorMessage.STUDENT_NOT_REGISTRATION);
        }

        return em.createQuery("SELECT new com.auto.check.web.dto.StudentAttendanceResponseDTO(a.id, a.week, li.lectureTime.day_of_week, a.isAttend)\n" +
                        "        FROM Attendance a\n" +
                        "        JOIN FETCH LectureInfo li ON li=a.lectureInfo\n" +
                        "        WHERE li.lecture.id=:lectureId AND a.user.id=:userId")
                .setParameter("userId", loginUser.getId())
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    public void singUpClass(Long lectureId) {

        User user = userService.getLoginUserInfo();

        lectureMapper.insertRegistration(user.getId(), lectureId);

        Lecture lecture = em.find(Lecture.class, lectureId);

        for (int i = 1; i <= 14; i++) {
            for (var l : lecture.getLectureInfoList()) {
                Attendance attendance = Attendance.builder()
                        .user(user)
                        .week(i)
                        .isAttend((short) 0)
                        .lectureInfo(l).build();

                em.persist(attendance);
            }
        }
    }

    public Lecture createLecture(LectureCreateRequestDTO lecture) {

        User user = em.find(User.class, lecture.getProfessorId());

        if (user == null) throw new NonCriticalException(ErrorMessage.USER_NOT_EXIST);

        if (!user.getUser_type().equals(UserType.PROFESSOR)) {
            throw new NonCriticalException(ErrorMessage.USER_NOT_PROFESSOR);
        }

        Lecture newLecture = Lecture.builder()
                .lectureName(lecture.getLectureName())
                .professor(user)
                .semester(lecture.getSemester()).build();

        em.persist(newLecture);

        return newLecture;
    }

    public LectureInfo createLectureInfo(LectureInfo lectureInfo){

        if(em.find(Lecture.class, lectureInfo.getLecture().getId()) == null){
            throw new NonCriticalException(ErrorMessage.LECTURE_NOT_EXIST);
        }

        em.persist(lectureInfo);

        return lectureInfo;
    }
}
