package com.auto.check.api.dto;

import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LectureDTO {
    private Long lectureId;
    private String lectureName;
    private List<UserLectureInfoResponse> lectureInfoList;

    public LectureDTO(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.lectureInfoList = lecture.getLectureInfoList()
                .stream()
                .map(UserLectureInfoResponse::new)
                .collect(Collectors.toList());
    }
}
