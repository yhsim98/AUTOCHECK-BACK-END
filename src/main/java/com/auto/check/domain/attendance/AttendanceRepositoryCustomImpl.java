package com.auto.check.domain.attendance;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.lectureinfo.QLectureInfo;
import com.auto.check.domain.user.QUser;
import com.auto.check.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.auto.check.domain.attendance.QAttendance.*;
import static com.auto.check.domain.lectureinfo.QLectureInfo.*;
import static com.auto.check.domain.user.QUser.*;

@RequiredArgsConstructor
public class AttendanceRepositoryCustomImpl implements AttendanceRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Attendance findAttendanceById(Long id){
        return queryFactory.selectFrom(attendance)
                .where(attendance.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Attendance> findAttendanceByLectureInfoAndWeek(LectureInfo li, int week) {
        return queryFactory.selectFrom(attendance)
                .innerJoin(attendance.lectureInfo, lectureInfo)
                .fetchJoin()
                .innerJoin(attendance.user, user)
                .fetchJoin()
                .distinct()
                .where(attendance.week.eq(week))
                .where(attendance.lectureInfo.id.eq(li.getId()))
                .fetch();
    }

    @Override
    public void updateAttendancesByLectureInfoAndWeek(List<User> students, LectureInfo lectureInfo, int week) {
        queryFactory.update(attendance)
                .set(attendance.isAttend, (short)1)
                .where(attendance.user.in(students))
                .where(attendance.lectureInfo.eq(lectureInfo))
                .where(attendance.week.eq(week));
    }
}
