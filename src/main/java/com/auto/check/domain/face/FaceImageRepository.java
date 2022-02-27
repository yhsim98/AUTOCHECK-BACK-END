package com.auto.check.domain.face;

import com.auto.check.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaceImageRepository extends JpaRepository<FaceImage, Long> {
    List<FaceImage> findByUser(User user);
    Optional<FaceImage> findByIdAndUser(Long id, User user);
}
