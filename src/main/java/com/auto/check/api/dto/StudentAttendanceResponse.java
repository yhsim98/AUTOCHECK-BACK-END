package com.auto.check.api.dto;

import com.auto.check.domain.attendance.Attendance;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StudentAttendanceResponse {
    private Long attendanceId;
    private Long userId;
    private String name;
    private String schoolNumber;
    private Short isAttend;

    public StudentAttendanceResponse(Attendance attendance) {
        this.attendanceId = attendance.getId();
        this.userId = attendance.getUser().getId();
        this.name = attendance.getUser().getName();
        this.schoolNumber = attendance.getUser().getSchoolNumber();
        this.isAttend = attendance.getIsAttend();
    }
}
