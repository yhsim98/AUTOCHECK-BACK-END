package com.auto.check.api.dto;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.value.LectureRoom;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LectureInfoResponse {
    private Long lectureId;
    private List<UserLectureInfoResponse> lectureInfo;
    public LectureInfoResponse(Long lectureId, List<LectureInfo> lectureInfoList) {
        this.lectureId = lectureId;
        this.lectureInfo = lectureInfoList.stream()
                .map(UserLectureInfoResponse::new)
                .collect(Collectors.toList());
    }
}

