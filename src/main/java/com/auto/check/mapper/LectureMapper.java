package com.auto.check.mapper;

import com.auto.check.domain.Lecture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureMapper {
    List<Lecture> getLectureInfoList(Long userId);
}
