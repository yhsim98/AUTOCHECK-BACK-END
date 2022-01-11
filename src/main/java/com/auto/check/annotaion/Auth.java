package com.auto.check.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    // 루트, 관리자, default
    enum Role { ROOT, PROFESSOR, STUDENT }

    // 강의자료 권한, 강의평 권한, 시간표 권한 , default
    enum Authority { LectureBank, Lecture, TimeTable, NONE }

    Role role() default Role.STUDENT;
    Authority authority() default Authority.NONE;
}
