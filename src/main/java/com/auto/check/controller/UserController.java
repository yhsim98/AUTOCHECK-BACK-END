package com.auto.check.controller;

import com.auto.check.domain.User;
import com.auto.check.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value="로그인 요청", notes="로그인 요청하는 api, 문제없다면 token 반환")
    public ResponseEntity userLogin(@RequestBody User user){
        return new ResponseEntity(userService.userLogin(user), HttpStatus.OK);
    }

    @GetMapping("/me")
    @ApiOperation(value="회원 정보 API", notes="회원 정보를 가져오는 API", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLoginUserInfo(){
        return new ResponseEntity(userService.getLoginUserInfo(), HttpStatus.OK);
    }

    @PostMapping("/sing-in")
    @ApiOperation(value = "회원가입 API", notes="유저회원가입 API 입니다. ")
    public ResponseEntity singIn(@RequestBody User user){
        userService.singInUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
