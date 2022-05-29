package com.auto.check.service.dto;

import com.auto.check.domain.face.FaceImage;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestFaceCheckDTO {
    private Long lectureId;
    private Long lectureInfoId;
    private int week;
    private List<Long> studentList;

    public RequestFaceCheckDTO(Lecture lecture, Long lectureInfoId, List<User> students, int week) {
        this.lectureId = lecture.getId();
        this.lectureInfoId = lectureInfoId;
        this.studentList = students.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        this.week = week;
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @Getter
    @Setter
    class Student{
        private Long studentId;
        //private List<String> url;

        public Student(User student) {
            this.studentId = student.getId();
//            this.url = student.getFaceImages().stream()
//                    .map(FaceImage::getSavedUrl)
//                    .collect(Collectors.toList());
        }
    }
}


