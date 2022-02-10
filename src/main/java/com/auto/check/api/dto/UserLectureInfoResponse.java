package com.auto.check.api.dto;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLectureInfoResponse{
    private Long lectureInfoId;
    private String lectureRoom;
    private LectureTime lectureTime;

    public UserLectureInfoResponse(LectureInfo lectureInfo) {
        this.lectureInfoId = lectureInfo.getId();
        this.lectureRoom = lectureInfo.getLectureRoom().getLecture_room();
        this.lectureTime = lectureInfo.getLectureTime();
    }
}
