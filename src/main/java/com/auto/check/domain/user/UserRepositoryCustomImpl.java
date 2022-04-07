package com.auto.check.domain.user;

import com.auto.check.domain.face.QFaceImage;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.studentlecture.QStudentLecture;
import com.auto.check.domain.studentlecture.StudentLecture;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.auto.check.domain.face.QFaceImage.*;
import static com.auto.check.domain.studentlecture.QStudentLecture.studentLecture;
import static com.auto.check.domain.user.QUser.user;


@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findLectureRelatedUsers(Lecture lecture) {
        return jpaQueryFactory.selectFrom(user)
                .innerJoin(studentLecture)
                .on(studentLecture.student().eq(user))
                .where(studentLecture.lecture().eq(lecture))
                .fetch();
    }
}
