package com.auto.check.web.dto;

import lombok.Getter;

@Getter
public class StudentAttendanceResponseDTO {
    private Long attendanceId;
    private int week;
    private String day_of_week;
    private Short isAttend;

    public StudentAttendanceResponseDTO(Long attendanceId, int week, String day_of_week, Short isAttend) {
        this.attendanceId = attendanceId;
        this.week = week;
        this.day_of_week = day_of_week;
        this.isAttend = isAttend;
    }
}
