package com.auto.check.domain.value;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class LectureTime {
    private String day_of_week;
    private String lecture_start;
    private String lecture_end;

    @Builder
    public LectureTime(String lecture_start, String day_of_week, String lecture_end) {
        this.lecture_start = lecture_start;
        this.day_of_week = day_of_week;
        this.lecture_end = lecture_end;
    }
}
