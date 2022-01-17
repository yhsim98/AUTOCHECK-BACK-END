package com.auto.check.service;

import com.auto.check.domain.user.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.mapper.UserMapper;
import com.auto.check.util.EntityManagerFactoryUtil;
import com.auto.check.util.Jwt;
import com.auto.check.web.dto.UserLoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final Jwt jwt;
    private final UserMapper userMapper;
    @PersistenceContext
    EntityManager em;

    public Map userLogin(UserLoginRequestDTO user) {

        User loginUser = userMapper.getPasswordByAccount(user.getAccount());

        if(loginUser == null){
            throw new NonCriticalException(ErrorMessage.ACCOUNT_NOT_EXIST);
        }

        if(!BCrypt.checkpw(user.getPassword(), loginUser.getPassword())){
            throw new NonCriticalException(ErrorMessage.WRONG_PASSWORD_EXCEPTION);
        }

        Map<String, String> token = new HashMap<>();

        token.put("access_token", jwt.generateToken(loginUser.getId(), loginUser.getUser_type()));

        System.out.println("token : " + token.get("access_token"));
        return token;
    }

    public User getLoginUserInfo() {

        User user = null;


        user = em.find(User.class, this.getLoginUserIdFromJwt());

        if (user == null) {
            throw new NonCriticalException(ErrorMessage.USER_NOT_EXIST);
        }

        return user;
    }

    public void singInUser(User user) {
        if (userMapper.getUserByAccount(user.getAccount()) != null) {
            throw new NonCriticalException(ErrorMessage.ACCOUNT_ALREADY_EXIST);
        }

        if(userMapper.getUserByNumber(user.getSchool_number()) != null){
            throw new NonCriticalException(ErrorMessage.SCHOOL_NUMBER_ALREADY_EXIST);
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        userMapper.insertUser(user);
    }

    private Long getLoginUserIdFromJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        jwt.isValid(token);

        return Long.valueOf(String.valueOf(jwt.getClaimsFromJwt(token).get("id")));
    }
}