package com.auto.check.api;

import com.auto.check.annotaion.Auth;
import com.auto.check.api.dto.FaceImageDTO;
import com.auto.check.api.dto.UserDTO;
import com.auto.check.api.response.BaseResponse;
import com.auto.check.domain.face.FaceImage;
import com.auto.check.domain.user.User;
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
    public ResponseEntity singIn(@RequestBody User user){
        userService.singInUser(user);
        return new ResponseEntity(BaseResponse.of(HttpStatus.OK), HttpStatus.CREATED);
    }

    @Auth
    @PostMapping(path= "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value="사진등록 API", notes="유저 사진등록 api", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity userFaceImages(@RequestParam("file") List<MultipartFile> images) throws IOException {
        List<FaceImageDTO> result = userService.saveImages(images).stream()
                .map(FaceImageDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity(BaseResponse.of(result, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @Auth
    @GetMapping(path="/image")
    @ApiOperation(value="저장되어있는 사진의 url을 반환합니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity userFaceImageList(){
        List<FaceImageDTO> result = userService.saveImages(new ArrayList<>()).stream()
                .map(FaceImageDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity(BaseResponse.of(result, HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @DeleteMapping(path="/image")
    @ApiOperation(value="저장된 사진 삭제, 사진 id 주시면 됩니다", authorizations = @Authorization(value="Bearer +accessToken"))
    public ResponseEntity faceImageDelete(@RequestParam Long imageId){
        userService.deleteImage(imageId);
        return new ResponseEntity(BaseResponse.of(HttpStatus.OK), HttpStatus.OK);
    }
}
