package com.auto.check.api.dto;

import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StudentLectureAttendanceResponse {
    private Long lectureInfoId;
    private String dayOfWeek;
    private List<AttendanceDTO> attendanceList;

    public StudentLectureAttendanceResponse(LectureInfo lectureInfo, List<Attendance> attendanceList) {
        this.lectureInfoId = lectureInfo.getId();
        this.dayOfWeek = lectureInfo.getLectureTime().getDay_of_week();
        this.attendanceList = attendanceList.stream()
                .map(AttendanceDTO::new)
                .collect(Collectors.toList());
    }
}

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class AttendanceDTO{
    private Long attendanceId;
    private int week;
    private Short isAttend;

    public AttendanceDTO(Attendance attendance) {
        this.attendanceId = attendance.getId();
        this.week = attendance.getWeek();
        this.isAttend = attendance.getIsAttend();
    }
}
