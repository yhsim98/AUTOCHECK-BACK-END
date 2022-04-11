package com.auto.check.api;

import com.auto.check.annotaion.Auth;
import com.auto.check.api.dto.FaceImageDTO;
import com.auto.check.api.dto.SignInRequestDTO;
import com.auto.check.api.dto.UserDTO;
import com.auto.check.api.response.BaseResponse;
import com.auto.check.service.FaceImageService;
import com.auto.check.service.UserService;
import com.auto.check.api.dto.LoginRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final FaceImageService faceImageService;

    @PostMapping("/login")
    @ApiOperation(value="로그인 요청", notes="로그인 요청하는 api, 문제없다면 token 반환")
    public ResponseEntity userLogin(@RequestBody LoginRequestDTO requestDTO){
        return new ResponseEntity(BaseResponse.of(userService.userLogin(requestDTO), HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @GetMapping("/me")
    @ApiOperation(value="회원 정보 API", notes="회원 정보를 가져오는 API", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLoginUserInfo(){
        return new ResponseEntity(BaseResponse.of(new UserDTO(userService.getLoginUserInfo()), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/sing-in")
    @ApiOperation(value = "회원가입 API", notes="유저회원가입 API 입니다. ")
    public ResponseEntity singIn(@RequestBody SignInRequestDTO request){
        userService.singInUser(request.toEntity());
        return new ResponseEntity(BaseResponse.of(HttpStatus.CREATED), HttpStatus.CREATED);
    }

}
