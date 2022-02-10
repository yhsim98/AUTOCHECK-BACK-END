package com.auto.check.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfessorAttendanceResponseDTO {
    private Long attendanceId;
    private Long userId;
    private String school_number;
    private String name;
    private int week;
    private String day_of_week;
    private Short isAttend;

    public ProfessorAttendanceResponseDTO(Long attendanceId, Long userId, String school_number, String name, int week, String day_of_week, Short isAttend) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.school_number = school_number;
        this.name = name;
        this.week = week;
        this.day_of_week = day_of_week;
        this.isAttend = isAttend;
    }
}
