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
    private List<LectureInfoDTO> lectureInfo;
    public LectureInfoResponse(Long lectureId, List<LectureInfo> lectureInfoList) {
        this.lectureId = lectureId;
        this.lectureInfo = lectureInfoList.stream()
                .map(LectureInfoDTO::new)
                .collect(Collectors.toList());
    }
}

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class LectureInfoDTO{
    private Long lectureInfoId;
    private LectureTime lectureTime;
    private LectureRoom lectureRoom;

    public LectureInfoDTO(LectureInfo lectureInfo) {
        this.lectureInfoId = lectureInfo.getId();
        this.lectureTime = lectureInfo.getLectureTime();
        this.lectureRoom = lectureInfo.getLectureRoom();
    }
}
