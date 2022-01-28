package com.auto.check.web;

import com.auto.check.config.ResponseData;
import com.auto.check.domain.attendance.AttendanceRepository;
import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserRepository;
import com.auto.check.service.UserService;
import com.auto.check.web.dto.LoginRequestDTO;
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
    public ResponseEntity userLogin(@RequestBody LoginRequestDTO requestDTO){
        return new ResponseEntity(ResponseData.of(userService.userLogin(requestDTO), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/me")
    @ApiOperation(value="회원 정보 API", notes="회원 정보를 가져오는 API", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLoginUserInfo(){
        return new ResponseEntity(ResponseData.of(userService.getLoginUserInfo(), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/sing-in")
    @ApiOperation(value = "회원가입 API", notes="유저회원가입 API 입니다. ")
    public ResponseEntity singIn(@RequestBody User user){
        userService.singInUser(user);
        return new ResponseEntity(ResponseData.of(HttpStatus.OK), HttpStatus.CREATED);
    }

}
