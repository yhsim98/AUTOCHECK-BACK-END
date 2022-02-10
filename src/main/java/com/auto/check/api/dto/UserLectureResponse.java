package com.auto.check.api.dto;

import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLectureResponse {
    private Long lectureId;
    private String lectureName;
    private List<UserLectureInfoResponse> lectureInfoList;

    public UserLectureResponse(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.lectureInfoList = lecture.getLectureInfoList().stream()
                .map(UserLectureInfoResponse::new)
                .collect(Collectors.toList());
    }
}

