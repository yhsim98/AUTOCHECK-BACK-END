package com.auto.check.web.dto;

import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.value.LectureRoom;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Time;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class LectureInfoCreateRequestDTO {
    private Long lectureId;
    private String lectureRoom;
    private String dayOfWeek;
    @JsonFormat(pattern = "HH:mm:ss")
    private Time lectureStart;
    @JsonFormat(pattern = "HH:mm:ss")
    private Time lectureEnd;

    public LectureInfo toEntity(Lecture lecture){
        return LectureInfo.builder()
                .lecture(lecture)
                .lectureRoom(LectureRoom.builder().lecture_room(lectureRoom).cctv_ip("1").build())
                .lectureTime(LectureTime.builder()
                        .lecture_start(lectureStart)
                        .lecture_end(lectureEnd)
                        .day_of_week(dayOfWeek).build())
                .build();
    }
}
