package com.auto.check.controller;

import com.auto.check.annotaion.Auth;
import com.auto.check.service.LectureService;
import com.auto.check.service.UserService;
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

    // 출석여부 변경, 교수만 자기의 수업을 수강하는 학생의 출석여부 변경 가능
    @PatchMapping(value="/student")
    public ResponseEntity alterStudentAttendance(@RequestParam Long studentId, @RequestParam String lectureInfoId){
        return null;
    }

    // 특정 강의의 수강학생 및 출석여부 조회
    @GetMapping(value="/student")
    public ResponseEntity getLectureStudent(@RequestParam(value = "lectureId") Long lectureId){
        System.out.println(lectureId + "Asd");
        return new ResponseEntity(lectureService.getLectureStudent(lectureId), HttpStatus.OK);
    }

    // 해당 유저의 강의 목록 조회
    @Auth
    @GetMapping(value="")
    public ResponseEntity getLectureList(){
        return new ResponseEntity(lectureService.getLectureList(), HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    public String test(){
        return "test";
    }

}
