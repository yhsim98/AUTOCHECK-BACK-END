package com.auto.check.controller;

import com.auto.check.annotaion.Auth;
import com.auto.check.service.LectureService;
import com.auto.check.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/lecture")
@RequiredArgsConstructor
public class LectureController {

    public final UserService userService;
    public final LectureService lectureService;

    // 특정 강의의 출석여부 조회
    @Auth
    @GetMapping(value="/student")
    @ApiOperation(value="출석여부 조회", notes="lectureId를 쿼리로 받아 해당 강의의 출석여부를 확인합니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLectureStudent(@RequestParam(value = "lectureId") Long lectureId){
        return new ResponseEntity(lectureService.getLectureStudent(lectureId), HttpStatus.OK);
    }

    // 해당 유저의 강의 목록 조회
    @Auth
    @GetMapping(value="")
    @ApiOperation(value="강의 목록 조회", notes="해당 유저의 강의 혹은 수강하는 강의의 목록 반환", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLectureList(){
        return new ResponseEntity(lectureService.getLectureList(), HttpStatus.OK);
    }

    @Auth
    @PostMapping(value="/registration")
    @ApiOperation(value="수강신청", notes="수강신청 API 토큰과 함께 수강하고자 하는 강의의 ID 를 보내면 됩니다. \n 출석정보도 같이 생성이 됩니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity singUpClass(@RequestParam Long lectureId){
        lectureService.singUpClass(lectureId);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
