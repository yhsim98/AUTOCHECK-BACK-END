package com.auto.check.domain.lecture;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE lecture SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name="lecture")
@NoArgsConstructor
public class Lecture extends DefaultEntity {

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureInfo> lectureInfoList = new ArrayList<>();

    @Column(name = "lecture_name")
    private String lectureName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="professor_id")
    private User professor;

    @Builder
    public Lecture(String lectureName, User professor, String semester) {
        this.lectureName = lectureName;
        this.professor = professor;
    }
}
