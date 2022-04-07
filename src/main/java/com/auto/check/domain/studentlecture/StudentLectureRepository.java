package com.auto.check.domain.studentlecture;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLectureRepository extends JpaRepository<StudentLecture, Long> {
    int countByStudentIdAndLectureId(Long studentId, Long lectureId);
}
