package com.auto.check.web;

import com.auto.check.annotaion.Auth;
import com.auto.check.config.ResponseData;
import com.auto.check.service.AttendanceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    public final AttendanceService attendanceService;

    @GetMapping(value = "/test")
    public ResponseEntity test(){
        HashMap<String, String> map = new HashMap<>();
        map.put("text", "test response");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // 출석여부 변경, 교수만 자기의 수업을 수강하는 학생의 출석여부 변경 가능
    @Auth
    @PatchMapping(value="/student")
    @ApiOperation(value = "출석여부변경 API", notes="출석여부를 변경하는 API 입니다. role 이 professor 이어야 사용가능합니다. \n week(해당 주차)와 studentId 를 받습니다. \n 출석이면 미출석, 미출석이면 출석으로 변경합니다.")
    public ResponseEntity alterStudentAttendance(@RequestParam Long attendanceId) {
        attendanceService.alterStudentAttendance(attendanceId);
        return new ResponseEntity(ResponseData.of(HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @GetMapping(value="/student")
    @ApiOperation(value = "해당 주차의 학생 출석 리스트", notes="해당 주차의 학생 출석 리스트를 반환하는 API 입니다. role 이 professor 이어야 사용가능합니다. \n week(해당 주차)와 lectureInfoId 를 받습니다.")
    public ResponseEntity getStudentAttendanceList(@RequestParam int week,
                                                   @RequestParam Long lectureInfoId) {

        return new ResponseEntity(ResponseData.of(attendanceService.getStudentsAttendanceList(week, lectureInfoId), HttpStatus.OK), HttpStatus.OK);
    }
}
