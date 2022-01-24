package com.auto.check.domain;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.value.LectureRoom;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="lecture_info")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LectureInfo extends DefaultEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Embedded
    private LectureRoom lectureRoom;

    @Embedded
    private LectureTime lectureTime;

    @OneToMany(mappedBy = "lectureInfo")
    @ApiModelProperty(hidden = true)
    private List<Attendance> attendanceList = new ArrayList<>();
}
