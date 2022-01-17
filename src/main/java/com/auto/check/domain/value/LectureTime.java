package com.auto.check.domain.value;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.sql.Time;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class LectureTime {
    private String day_of_week;
    private Time lecture_start;
    private Time lecture_end;

    @Builder
    public LectureTime(Time lecture_start, String day_of_week, Time lecture_end) {
        this.lecture_start = lecture_start;
        this.day_of_week = day_of_week;
        this.lecture_end = lecture_end;
    }
}
