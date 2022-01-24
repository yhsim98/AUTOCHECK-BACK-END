package com.auto.check.domain.registration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    int countByStudentIdAndLectureId(Long studentId, Long lectureId);
}
