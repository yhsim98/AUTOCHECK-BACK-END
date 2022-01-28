package com.auto.check.web.dto;

import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.value.LectureTime;
import lombok.Getter;

@Getter
public class UserLectureInfoResponseDTO {
    private Long lectureInfoId;
    private Long lectureId;
    private String lectureName;
    private String lectureRoom;
    private LectureTime lectureTime;

    public UserLectureInfoResponseDTO(Long lectureInfoId, Long lectureId, String lectureName, String lectureRoom, LectureTime lectureTime) {
        this.lectureInfoId = lectureInfoId;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureRoom = lectureRoom;
        this.lectureTime = lectureTime;
    }

    public UserLectureInfoResponseDTO(LectureInfo lectureInfo) {
        this.lectureInfoId = lectureInfo.getId();
        this.lectureId = lectureInfo.getLecture().getId();
        this.lectureName = lectureInfo.getLecture().getLectureName();
        this.lectureRoom = lectureInfo.getLectureRoom().getLecture_room();
        this.lectureTime = lectureInfo.getLectureTime();
    }
}
