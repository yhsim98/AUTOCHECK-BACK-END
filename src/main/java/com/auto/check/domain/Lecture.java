package com.auto.check.domain;

import com.auto.check.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="lecture")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Lecture extends DefaultEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(hidden = true)
    private List<LectureInfo> lectureInfoList = new ArrayList<>();

    @Column(name = "lecture_name")
    private String lectureName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="professor_id")
    private User professor;

    private String semester;

    @Builder
    public Lecture(String lectureName, User professor, String semester) {
        this.lectureName = lectureName;
        this.professor = professor;
        this.semester = semester;
    }
}
