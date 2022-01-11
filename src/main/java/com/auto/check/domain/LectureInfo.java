package com.auto.check.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LectureInfo {
    private Long id;
    private Long lecture_id;
    private String lecture_room;
    private String cctv_ip;
    private String day_of_week;
    private Time lecture_start;
    private Time lecture_end;
}
