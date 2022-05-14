package com.auto.check.domain.lectureinfo;

import com.auto.check.domain.value.LectureTime;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LectureInfoCustomImpl implements LectureInfoCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<LectureInfo> findLectureInfoByLectureTime(LectureTime lectureTime) {
        return queryFactory.selectFrom(QLectureInfo.lectureInfo)
                .where(QLectureInfo.lectureInfo.lectureTime.day_of_week.eq(lectureTime.getDay_of_week()))
                .where(QLectureInfo.lectureInfo.lectureTime.lecture_start.eq(lectureTime.getLecture_start()))
                .fetch();
    }
}
