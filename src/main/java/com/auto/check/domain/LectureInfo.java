package com.auto.check.domain;

import com.auto.check.domain.value.LectureRoom;
import com.auto.check.domain.value.LectureTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="lecture_info")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LectureInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Embedded
    private LectureRoom lectureRoom;

    @Embedded
    private LectureTime lectureTime;

    @OneToMany(mappedBy = "lectureInfo", orphanRemoval = true)
    @ApiModelProperty(hidden = true)
    private List<Attendance> attendanceList = new ArrayList<>();
}
