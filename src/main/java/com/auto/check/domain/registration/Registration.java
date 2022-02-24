package com.auto.check.domain.registration;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="registration")
public class Registration extends DefaultEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder
    public Registration(User student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }
}
