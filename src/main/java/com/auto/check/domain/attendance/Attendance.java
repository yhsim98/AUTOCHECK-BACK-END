package com.auto.check.domain.attendance;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@Table(name="attendance")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE Attendance SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
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
    public Attendance(User user, int week, LectureInfo lectureInfo, Short isAttend) {
        this.user = user;
        this.week = week;
        this.lectureInfo = lectureInfo;
        this.isAttend = isAttend;
    }

    public void updateIsAttend(){
        this.isAttend = this.isAttend == 0 ? (short)1 : 0;
    }


}