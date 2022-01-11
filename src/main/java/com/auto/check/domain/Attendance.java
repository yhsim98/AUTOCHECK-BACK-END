package com.auto.check.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attendance {
    private Long id;
    private Long user_id;
    private int week;
    private String day_of_week;
    private Long lecture_info_id;
    private Short is_attend;
    private Timestamp created_at;
    private Timestamp modified_at;
}