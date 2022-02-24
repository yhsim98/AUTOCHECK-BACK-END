package com.auto.check.domain.face;

import com.auto.check.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaceImageRepository extends JpaRepository<FaceImage, Long> {
    List<FaceImage> findByUser(User user);
}
