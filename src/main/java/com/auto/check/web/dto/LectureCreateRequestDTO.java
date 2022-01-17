package com.auto.check.web.dto;

import com.auto.check.domain.Lecture;
import com.auto.check.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LectureCreateRequestDTO {
    private String lectureName;
    private Long professorId;
    private String semester;

}
