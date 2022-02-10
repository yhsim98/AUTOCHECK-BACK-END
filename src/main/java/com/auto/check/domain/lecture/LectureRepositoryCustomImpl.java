package com.auto.check.domain.lecture;

import com.auto.check.domain.lectureinfo.QLectureInfo;
import com.auto.check.domain.registration.QRegistration;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.auto.check.domain.lecture.QLecture.*;
import static com.auto.check.domain.lectureinfo.QLectureInfo.*;
import static com.auto.check.domain.registration.QRegistration.*;

@RequiredArgsConstructor
public class LectureRepositoryCustomImpl implements LectureRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Lecture> findUserLectureList(Long userId) {
        return queryFactory.selectFrom(lecture)
                .distinct()
                .innerJoin(registration.lecture(), lecture)
                .where(registration.student().id.eq(userId))
                .innerJoin(lecture.lectureInfoList, lectureInfo)
                .fetchJoin()
                .fetch();
    }
}
