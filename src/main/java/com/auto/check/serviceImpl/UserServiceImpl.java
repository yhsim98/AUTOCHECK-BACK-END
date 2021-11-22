package com.auto.check.serviceImpl;

import com.auto.check.domain.User;
import com.auto.check.mapper.UserMapper;
import com.auto.check.service.UserService;
import com.auto.check.util.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Jwt jwt;
    private final UserMapper userMapper;

    @Override
    public Map userLogin(User user) {
        User loginUser = userMapper.getPasswordByAccount(user.getAccount());

        if(loginUser == null){
            System.out.println("1");
        }

        if(!loginUser.getPassword().equals(user.getPassword())){
            System.out.println("2");
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

    private Long getLoginUserIdFromJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        jwt.isValid(token);

        return Long.valueOf(String.valueOf(jwt.getClaimsFromJwtToken(token).get("id")));
    }
}