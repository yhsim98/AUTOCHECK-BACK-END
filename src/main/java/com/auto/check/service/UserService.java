package com.auto.check.service;

import com.auto.check.domain.face.FaceImage;
import com.auto.check.domain.face.FaceImageRepository;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserRepository;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;

import com.auto.check.service.dto.RequestImageTrainDTO;
import com.auto.check.util.Jwt;
import com.auto.check.api.dto.LoginRequestDTO;
import com.auto.check.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Jwt jwt;
    private final UserRepository userRepository;

    public Map userLogin(LoginRequestDTO user) {
        User loginUser = userRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new NonCriticalException(ErrorMessage.ACCOUNT_NOT_EXIST));

        if(!BCrypt.checkpw(user.getPassword(), loginUser.getPassword())){
            throw new NonCriticalException(ErrorMessage.WRONG_PASSWORD_EXCEPTION);
        }

        Map<String, String> token = new HashMap<>();

        token.put("access_token", jwt.generateToken(loginUser.getId(), loginUser.getUserType()));

        return token;
    }

    public User getLoginUserInfo() {
        Long userId = getLoginUserIdFromJwt();
        return getUserInfoById(userId);
    }

    public User getUserInfoById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NonCriticalException(ErrorMessage.USER_NOT_EXIST));
    }

    @Transactional
    public void singInUser(User user) {
        if (userRepository.countByAccount(user.getAccount()) > 0) {
            throw new NonCriticalException(ErrorMessage.ACCOUNT_ALREADY_EXIST);
        }

        if(userRepository.countBySchoolNumber(user.getSchoolNumber()) > 0){
            throw new NonCriticalException(ErrorMessage.SCHOOL_NUMBER_ALREADY_EXIST);
        }

        user.changePasswordBcrypt(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);
    }

    private Long getLoginUserIdFromJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        jwt.isValid(token);

        return Long.valueOf(String.valueOf(jwt.getClaimsFromJwt(token).get("id")));
    }

    public List<User> getLectureRelatedUserAndFaces(Lecture lecture){
        return userRepository.findLectureRelatedUsersAndImages(lecture);
    }
}