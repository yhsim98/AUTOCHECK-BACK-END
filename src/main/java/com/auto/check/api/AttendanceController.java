package com.auto.check.api;

import com.auto.check.annotaion.Auth;
import com.auto.check.api.dto.PatchStudentAttendanceDTO;
import com.auto.check.api.dto.StudentAttendanceResponse;
import com.auto.check.api.dto.StudentLectureAttendanceResponse;
import com.auto.check.api.response.BaseResponse;
import com.auto.check.domain.attendance.Attendance;
import com.auto.check.domain.lectureinfo.LectureInfo;
import com.auto.check.service.AttendanceService;
import com.auto.check.service.dto.FaceCheckResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping(value = "/test")
    public ResponseEntity test(){
        HashMap<String, String> map = new HashMap<>();
        map.put("text", "test response");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // 출석여부 변경, 교수만 자기의 수업을 수강하는 학생의 출석여부 변경 가능
    @Auth
    @PatchMapping(value="/student")
    @ApiOperation(value = "출석여부변경 API", notes="출석여부를 변경하는 API 입니다. role 이 professor 이어야 사용가능합니다. \n week(해당 주차)와 studentId 를 받습니다. \n 출석이면 미출석, 미출석이면 출석으로 변경합니다.", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity alterStudentAttendance(@RequestParam Long attendanceId) {
        attendanceService.alterStudentAttendance(attendanceId);
        return new ResponseEntity(BaseResponse.of(HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @GetMapping(value="/professor/lecture")
    @ApiOperation(value = "해당 주차의 학생 출석 리스트", notes="특정 강의 해당 주차, 해당 요일의 모든 학생들의 출석정보를 반환하는 API 입니다. role 이 professor 이어야 사용가능합니다. \n week(해당 주차)와 lectureInfoId 를 받습니다.", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLectureStudentAttendanceListByWeek(@RequestParam int week,
                                                                @RequestParam Long lectureInfoId) {

        List<StudentAttendanceResponse> result = attendanceService.getLectureStudentsAttendanceList(week, lectureInfoId).stream()
                .map(StudentAttendanceResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity(BaseResponse.of(result, HttpStatus.OK), HttpStatus.OK);
    }
/*
    @Auth
    @PatchMapping(value="/auto-check")
    @ApiOperation(value = "출석시작", notes="출석시작 API, lecture_info_id 와 week 를 받습니다. \n 강의시간이 아닌 경우 예외가 발생합니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity startAutoCheck(@RequestParam Long lectureInfoId, @RequestParam int week){
        attendanceService.startFaceCheck(lectureInfoId, week);
        return new ResponseEntity(BaseResponse.of(HttpStatus.OK), HttpStatus.OK);
    }*/

    // 특정 강의의 출석여부 조회
    @Auth
    @GetMapping(value="/student/lecture") // lectureInfoId, dayOfWeek, [Attendance]
    @ApiOperation(value="출석여부 조회", notes="lectureId를 쿼리로 받아 해당 강의에 있는 요청한 학생의 모든 출석정보를 반환합니다.", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity<StudentLectureAttendanceResponse> getStudentLectureAttendanceList(@RequestParam(value = "lectureId") Long lectureId){

        Map<LectureInfo, List<Attendance>> collect = attendanceService.getStudentLectureAllAttendance(lectureId).stream()
                .collect(Collectors.groupingBy(Attendance::getLectureInfo));

        List<StudentLectureAttendanceResponse> result = collect.keySet().stream()
                .map(k->new StudentLectureAttendanceResponse(k, collect.get(k)))
                .collect(Collectors.toList());

        return new ResponseEntity(BaseResponse.of(result, HttpStatus.OK), HttpStatus.OK);
    }

    @PatchMapping(value="")
    @ApiOperation(value="자동 출석체크 api", notes="")
    public ResponseEntity patchStudentAttendance(PatchStudentAttendanceDTO request){
        attendanceService.patchStudentsAttendance(request.getLectureInfoId(), request.getStudentsId(), request.getWeek());
        return new ResponseEntity(BaseResponse.of(HttpStatus.CREATED), HttpStatus.CREATED);
    }

}
