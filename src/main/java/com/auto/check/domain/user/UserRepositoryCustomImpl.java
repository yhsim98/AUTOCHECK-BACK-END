package com.auto.check.domain.user;

import com.auto.check.domain.face.QFaceImage;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.registration.QRegistration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.auto.check.domain.face.QFaceImage.*;
import static com.auto.check.domain.registration.QRegistration.registration;
import static com.auto.check.domain.user.QUser.user;


@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findLectureRelatedUsersAndImages(Lecture lecture) {
        return jpaQueryFactory.selectFrom(user)
                .innerJoin(registration.student(), user)
                .innerJoin(user.faceImages, faceImage)
                .fetchJoin()
                .where(registration.lecture().eq(lecture))
                .fetch();
    }
}
