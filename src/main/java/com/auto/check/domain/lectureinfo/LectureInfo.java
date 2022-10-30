package com.auto.check.domain.lectureinfo;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.value.LectureRoom;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="lecture_info")
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE lecture_info SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LectureInfo extends DefaultEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Embedded
    private LectureRoom lectureRoom;

    @Embedded
    private LectureTime lectureTime;

    @Builder
    public LectureInfo(Lecture lecture, LectureRoom lectureRoom, LectureTime lectureTime) {
        this.lecture = lecture;
        this.lectureRoom = lectureRoom;
        this.lectureTime = lectureTime;
    }
}
