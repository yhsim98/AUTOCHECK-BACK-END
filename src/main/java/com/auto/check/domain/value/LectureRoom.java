package com.auto.check.domain.value;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class LectureRoom {
    private String lecture_room;
    private String cctv_ip;

    @Builder
    public LectureRoom(String lecture_room, String cctv_ip) {
        this.lecture_room = lecture_room;
        this.cctv_ip = cctv_ip;
    }
}
