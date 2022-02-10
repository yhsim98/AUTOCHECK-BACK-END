package com.auto.check.api.dto;

import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.user.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateLectureRequestDTO {
    private String lectureName;
    private Long professorId;
    private String semester;

    public Lecture toEntity(User user){
        return Lecture.builder()
                .lectureName(lectureName)
                .professor(user)
                .semester(semester).build();
    }
}
