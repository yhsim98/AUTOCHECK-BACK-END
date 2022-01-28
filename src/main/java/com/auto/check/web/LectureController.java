package com.auto.check.web;

import com.auto.check.annotaion.Auth;
import com.auto.check.config.ResponseData;
import com.auto.check.service.LectureService;
import com.auto.check.service.UserService;
import com.auto.check.web.dto.CreateRectureRequestDTO;
import com.auto.check.web.dto.LectureInfoCreateRequestDTO;
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
    @ApiOperation(value="출석여부 조회", notes="lectureId를 쿼리로 받아 해당 강의의 로그인한 학생 출석여부를 확인합니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLectureStudent(@RequestParam(value = "lectureId") Long lectureId){
        return new ResponseEntity(ResponseData.of(lectureService.getStudentLectureAttendanceList(lectureId), HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @GetMapping("/professor")
    @ApiOperation(value = "해당 강의 정보 반환", notes="강의 id 를 받으면 해당 강의 정보 반환", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLectureInfo(@RequestParam(value="lectureId") Long lectureId){
        return new ResponseEntity(ResponseData.of(lectureService.getLectureInfo(lectureId), HttpStatus.OK), HttpStatus.OK);
    }

    // 해당 유저의 강의 목록 조회
    @Auth
    @GetMapping(value="/user")
    @ApiOperation(value="강의 목록 조회", notes="해당 유저의 강의 혹은 수강하는 강의의 목록 반환", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getStudentLectureList(){
        return new ResponseEntity(ResponseData.of(lectureService.getLectureList(), HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @PostMapping(value="/registration")
    @ApiOperation(value="수강신청", notes="수강신청 API 토큰과 함께 수강하고자 하는 강의의 ID 를 보내면 됩니다. \n 출석정보도 같이 생성이 됩니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity singUpClass(@RequestParam Long lectureId){
        lectureService.singUpClass(lectureId);
        return new ResponseEntity(ResponseData.of(HttpStatus.OK), HttpStatus.CREATED);
    }

    @PostMapping(value="")
    @ApiOperation(value="강의생성", notes = "강의생성 API, 강의에 따른 강의정보는 일단 workbench 로 생성 바랍니다..")
    public ResponseEntity createLecture(@RequestBody CreateRectureRequestDTO lecture){
        return new ResponseEntity(ResponseData.of(lectureService.createLecture(lecture), HttpStatus.CREATED), HttpStatus.CREATED);
    }



    @PostMapping(value="/info")
    @ApiOperation(value="강의정보생성", notes = "그냥 workbench 로 생성하셔도 됩니다 \n lecture 의 id, lectureRoom 의 lecture_room, lectureTime 안의 day_of_week 는 요일이고 start, end 는 00:00:00 포맷으로 채워주시면 됩니다")
    public ResponseEntity createLectureInfo(@RequestBody LectureInfoCreateRequestDTO lectureInfo){
        return new ResponseEntity(ResponseData.of(lectureService.createLectureInfo(lectureInfo), HttpStatus.CREATED), HttpStatus.CREATED);
    }

}
