package com.auto.check.domain.attendance;

import com.auto.check.config.TestConfig;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lecture.LectureRepository;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.lectureinfo.LectureInfoRepository;
import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserRepository;
import com.auto.check.domain.user.UserType;
import com.auto.check.domain.value.LectureRoom;
import com.auto.check.domain.value.LectureTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.Time;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@EnableJpaAuditing
@Import(TestConfig.class)
public class AttendanceRepositoryTest {

    @Autowired AttendanceRepository attendanceRepository;
    @Autowired UserRepository userRepository;
    @Autowired
    LectureInfoRepository lectureInfoRepository;
    @Autowired
    LectureRepository lectureRepository;


    @Test void 출석정보_생성(){
        //given
    }

    @Test
    public void 출석정보_수정(){
        //given
        User user = createUser();
        Lecture lecture = createLecture(user);
        LectureInfo lectureInfo = createLectureInfo(lecture);

        Attendance attendance = attendanceRepository.save(Attendance.builder()
                        .user(user)
                        .isAttend((short) 0)
                        .lectureInfo(lectureInfo)
                        .week(1)
                        .build());

        //when
        attendanceRepository.bulkStudentAttendance(user.getId());

        //then
        assertThat(attendanceRepository.findById(attendance.getId()).get().getIsAttend()).isEqualTo((short)1);
    }

    @Test
    public void QueryDsl_테스트(){
        //given
        User user = createUser();
        Lecture lecture = createLecture(user);
        LectureInfo lectureInfo = createLectureInfo(lecture);

        Attendance attendance = attendanceRepository.save(Attendance.builder()
                .user(user)
                .isAttend((short) 0)
                .lectureInfo(lectureInfo)
                .week(1)
                .build());

        attendanceRepository.save(attendance);

        //when
        Attendance findAttendance = attendanceRepository.findAttendanceById(attendance.getId());

        //then
        assertThat(attendance).isEqualTo(findAttendance);
    }

    private User createUser(){
        User user = User.builder()
                .account("qwer")
                .password("2345")
                .name("asd").schoolNumber("as").userType(UserType.STUDENT)
                .build();

        userRepository.save(user);
        return user;
    }

    private Lecture createLecture(User user){
        Lecture lecture = lectureRepository.save(Lecture.builder()
                .lectureName("asd")
                .professor(user)
                .semester("1").build());

        return lecture;
    }

    private LectureInfo createLectureInfo(Lecture lecture){
        return lectureInfoRepository.save(LectureInfo.builder()
                        .lecture(lecture)
                        .lectureRoom(new LectureRoom("asd", "asd"))
                        .lectureTime(LectureTime.builder()
                                .lecture_end(new Time(1)).lecture_start(new Time(1)).day_of_week("1").build())
                .build());
    }

}
