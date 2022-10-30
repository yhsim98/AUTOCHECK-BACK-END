package com.auto.check.domain.studentlecture;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE student_lecture SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name="student_lecture")
public class StudentLecture extends DefaultEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder
    public StudentLecture(User student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }
}
