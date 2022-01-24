package com.auto.check.domain.attendance;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.LectureInfo;
import com.auto.check.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="attendance")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attendance extends DefaultEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private int week;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_info_id")
    private LectureInfo lectureInfo;

    @Column(name = "is_attend")
    private Short isAttend;

    @Builder
    public Attendance(Long id, User user, int week, LectureInfo lectureInfo, Short isAttend) {
        this.id = id;
        this.user = user;
        this.week = week;
        this.lectureInfo = lectureInfo;
        this.isAttend = isAttend;
    }

    public void updateIsAttend(){
        this.isAttend = this.isAttend == 0 ? (short)1 : 0;
    }
}