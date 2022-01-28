package com.auto.check.service;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.registration.RegistrationRepository;
import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserType;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.web.dto.CreateRectureRequestDTO;
import com.auto.check.web.dto.LectureInfoCreateRequestDTO;
import com.auto.check.web.dto.StudentAttendanceResponseDTO;
import com.auto.check.web.dto.UserLectureInfoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class LectureService {

    private final UserService userService;
    private final RegistrationRepository registrationRepository;
    @PersistenceContext
    EntityManager em;

    public List<UserLectureInfoResponseDTO> getLectureList() {
        User user = userService.getLoginUserInfo();

        List infoList;

        if (user.getUserType().equals(UserType.STUDENT)) {
             infoList = em.createQuery("SELECT new com.auto.check.web.dto.UserLectureInfoResponseDTO(li.id, li.lecture.id, li.lecture.lectureName, li.lectureRoom.lecture_room, li.lectureTime)\n" +
                            "        FROM LectureInfo li\n" +
                            "        JOIN li.lecture" +
                            "        WHERE li.lecture IN(SELECT r.lecture FROM Registration r WHERE r.student.id=:userId)")
                    .setParameter("userId", user.getId())
                    .getResultList();

        } else{
            infoList = em.createQuery("SELECT new com.auto.check.web.dto.UserLectureInfoResponseDTO(li.id, l.id, l.lectureName, li.lectureRoom.lecture_room, li.lectureTime)\n" +
                            "        FROM LectureInfo li\n" +
                            "        INNER JOIN Lecture l ON l.id = li.lecture.id" +
                            "        WHERE l.professor.id = :userId")
                    .setParameter("userId", user.getId())
                    .getResultList();
        }

        return infoList;
    }

    public List<UserLectureInfoResponseDTO> getLectureInfo(Long lectureId) {

        return em.createQuery("SELECT new com.auto.check.web.dto.UserLectureInfoResponseDTO(li.id, li.lecture.id, li.lecture.lectureName, li.lectureRoom.lecture_room, li.lectureTime)\n " +
                        "       FROM LectureInfo li\n"  +
                        "       INNER JOIN Lecture l ON l.id = li.lecture.id" +
                        "       WHERE l.id = :lectureId")
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    public List<StudentAttendanceResponseDTO> getStudentLectureAttendanceList(Long lectureId) {
        User loginUser = userService.getLoginUserInfo();

        if (registrationRepository.countByStudentIdAndLectureId(loginUser.getId(), lectureId) < 1) {
            throw new NonCriticalException(ErrorMessage.STUDENT_NOT_REGISTRATION);
        }

        return em.createQuery("SELECT new com.auto.check.web.dto.StudentAttendanceResponseDTO(a.id, a.week, li.lectureTime.day_of_week, a.isAttend)\n" +
                        "        FROM Attendance a\n" +
                        "        INNER JOIN LectureInfo li ON li = a.lectureInfo\n" +
                        "        WHERE li.lecture.id=:lectureId AND a.user.id=:userId")
                .setParameter("userId", loginUser.getId())
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    public void singUpClass(Long lectureId) {

        User user = userService.getLoginUserInfo();

        //lectureMapper.insertRegistration(user.getId(), lectureId);

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

    public Lecture createLecture(CreateRectureRequestDTO lecture) {

        User user = userService.getLoginUserInfo();

        if (!user.getUserType().equals(UserType.PROFESSOR)) {
            throw new NonCriticalException(ErrorMessage.USER_NOT_PROFESSOR);
        }

        Lecture newLecture = Lecture.builder()
                .lectureName(lecture.getLectureName())
                .professor(user)
                .semester(lecture.getSemester()).build();

        em.persist(newLecture);

        return newLecture;
    }

    public LectureInfo createLectureInfo(LectureInfoCreateRequestDTO lectureInfo){

        if(em.find(Lecture.class, lectureInfo.getLectureId()) == null){
            throw new NonCriticalException(ErrorMessage.LECTURE_NOT_EXIST);
        }

        em.persist(lectureInfo);

        return null;
    }
}
