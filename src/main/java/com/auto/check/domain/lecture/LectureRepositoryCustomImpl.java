package com.auto.check.domain.lecture;

import com.auto.check.domain.lectureinfo.QLectureInfo;
import com.auto.check.domain.studentlecture.QStudentLecture;
import com.auto.check.domain.user.User;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.auto.check.domain.lecture.QLecture.*;
import static com.auto.check.domain.lectureinfo.QLectureInfo.*;
import static com.auto.check.domain.studentlecture.QStudentLecture.studentLecture;
import static com.auto.check.domain.user.QUser.*;

@RequiredArgsConstructor
public class LectureRepositoryCustomImpl implements LectureRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Lecture> findUserLectureList(Long userId) {
        return queryFactory.selectFrom(lecture)
                .distinct()
                .innerJoin(studentLecture)
                .on(studentLecture.lecture().eq(lecture))
                .innerJoin(lecture.lectureInfoList, lectureInfo)
                .fetchJoin()
                .where(studentLecture.student().id.eq(userId))
                .fetch();
    }

    @Override
    public List<Lecture> findLectureByProfessor(User professor) {
        return queryFactory.selectFrom(lecture)
                .distinct()
                .innerJoin(lecture.lectureInfoList, lectureInfo)
                .fetchJoin()
                .where(lecture.professor.eq(professor))
                .fetch();
    }
}
