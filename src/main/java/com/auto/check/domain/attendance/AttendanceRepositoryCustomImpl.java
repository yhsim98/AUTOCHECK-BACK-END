package com.auto.check.domain.attendance;

import com.auto.check.domain.lectureinfo.QLectureInfo;
import com.auto.check.domain.user.QUser;
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
    public List<Attendance> findAttendancesByUserId(int week, Long lectureInfoId) {
        return queryFactory.selectFrom(attendance)
                .innerJoin(attendance.lectureInfo, lectureInfo)
                .fetchJoin()
                .innerJoin(attendance.user, user)
                .fetchJoin()
                .distinct()
                .where(attendance.week.eq(week))
                .where(attendance.lectureInfo.id.eq(lectureInfoId))
                .fetch();
    }
}
