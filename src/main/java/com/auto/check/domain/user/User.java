package com.auto.check.domain.user;

import com.auto.check.domain.DefaultEntity;
import com.auto.check.domain.face.FaceImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="user")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
public class User extends DefaultEntity {

    private String account;

    private String password;

    private String name;

    @Column(name="school_number")
    private String schoolNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaceImage> faceImages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name="user_type")
    private UserType userType;

    @Builder
    public User(String account, String password, String name, String schoolNumber, UserType userType) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.schoolNumber = schoolNumber;
        this.userType = userType;
    }

    public void changePasswordBcrypt(String password){
        this.password = password;
    }
}