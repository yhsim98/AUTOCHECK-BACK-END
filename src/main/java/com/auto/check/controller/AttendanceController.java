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

import java.util.HashMap;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    public final UserService userService;
    public final LectureService lectureService;

    @GetMapping(value = "/test")
    public ResponseEntity test(){
        HashMap<String, String> map = new HashMap<>();
        map.put("text", "test response");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // 출석여부 변경, 교수만 자기의 수업을 수강하는 학생의 출석여부 변경 가능
    @PatchMapping(value="/student")
    @ApiOperation(value = "출석여부변경 API", notes="출석여부를 변경하는 API 입니다. role 이 professor 이어야 사용가능합니다. \n studentId와 attendanceId를 받습니다 ")
    public ResponseEntity alterStudentAttendance(@RequestParam Long studentId, @RequestParam Long attendanceId){
        return null;
    }

}
