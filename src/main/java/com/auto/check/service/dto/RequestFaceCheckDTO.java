package com.auto.check.service.dto;

import com.auto.check.domain.face.FaceImage;
import com.auto.check.domain.lecture.Lecture;
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
    private List<Student> StudentList;

    public RequestFaceCheckDTO(Lecture lecture, List<User> students) {
        this.lectureId = lecture.getId();
        this.StudentList = students.stream()
                .map(Student::new)
                .collect(Collectors.toList());
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


