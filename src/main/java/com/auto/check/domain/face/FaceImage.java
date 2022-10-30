package com.auto.check.domain.face;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE face_image SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name="face_image")
public class FaceImage extends DefaultEntity {
    private String savedUrl;
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public FaceImage(String savedUrl, String fileName, User user) {
        this.savedUrl = savedUrl;
        this.fileName = fileName;
        this.user = user;
    }
}
