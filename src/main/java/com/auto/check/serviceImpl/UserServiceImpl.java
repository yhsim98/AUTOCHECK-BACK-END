package com.auto.check.serviceImpl;

import com.auto.check.domain.User;
import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;
import com.auto.check.mapper.UserMapper;
import com.auto.check.service.UserService;
import com.auto.check.util.Jwt;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Jwt jwt;
    private final UserMapper userMapper;

    @Override
    public Map userLogin(User user) {
        User loginUser = userMapper.getPasswordByAccount(user.getAccount());

        if(loginUser == null){
            throw new NonCriticalException(ErrorMessage.ACCOUNT_NOT_EXIST);
        }

        if(!BCrypt.checkpw(user.getPassword(), loginUser.getPassword())){
            throw new NonCriticalException(ErrorMessage.WRONG_PASSWORD_EXCEPTION);
        }

        Map<String, String> token = new HashMap<>();

        token.put("access_token", jwt.generateToken(loginUser.getId()));

        System.out.println("token : " + token.get("access_token"));
        return token;
    }

    @Override
    public User getLoginUserInfo() {
        return userMapper.getUserById(getLoginUserIdFromJwt());
    }

    @Override
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

        return Long.valueOf(String.valueOf(jwt.getClaimsFromJwtToken(token).get("id")));
    }
}