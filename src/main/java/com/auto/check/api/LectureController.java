package com.auto.check.api;

import com.auto.check.annotaion.Auth;
import com.auto.check.api.dto.LectureDTO;
import com.auto.check.api.dto.LectureInfoResponse;
import com.auto.check.api.dto.UserLectureResponse;
import com.auto.check.api.response.BaseResponse;
import com.auto.check.domain.lecture.Lecture;
import com.auto.check.domain.user.User;
import com.auto.check.service.AttendanceService;
import com.auto.check.service.LectureService;
import com.auto.check.api.dto.CreateLectureRequestDTO;
import com.auto.check.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final AttendanceService attendanceService;
    private final UserService userService;


    @Auth
    @GetMapping("/professor")
    @ApiOperation(value = "해당 강의 정보 반환", notes="강의 id 를 받으면 해당 강의 정보 반환", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getLectureInfo(@RequestParam(value="lectureId") Long lectureId){
        Lecture lecture = lectureService.getLectureById(lectureId);
        LectureInfoResponse response = new LectureInfoResponse(lecture, lectureService.getLectureInfoList(lectureId));
        return new ResponseEntity(BaseResponse.of(response, HttpStatus.OK), HttpStatus.OK);
    }

    // 해당 유저의 강의 목록 조회
    @Auth
    @GetMapping(value="/user")
    @ApiOperation(value="강의 목록 조회", notes="해당 유저의 강의 혹은 수강하는 강의의 목록과 각 강의 정보 리스트 반환", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity getUserLectureList(){
        List<UserLectureResponse> response = lectureService.findUserLectureList().stream()
                .map(UserLectureResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity(BaseResponse.of(response, HttpStatus.OK), HttpStatus.OK);
    }

    @Auth
    @PostMapping(value="/registration")
    @ApiOperation(value="수강신청", notes="수강신청 API 토큰과 함께 수강하고자 하는 강의의 ID 를 보내면 됩니다. \n 출석정보도 같이 생성이 됩니다", authorizations = @Authorization(value = "Bearer +accessToken"))
    public ResponseEntity singUpClass(@RequestParam Long lectureId){
        attendanceService.singUpClass(lectureId);
        return new ResponseEntity(BaseResponse.of(HttpStatus.OK), HttpStatus.CREATED);
    }

    @PostMapping(value="")
    @ApiOperation(value="강의생성", notes = "강의생성 API, 강의에 따른 강의정보는 일단 workbench 로 생성 바랍니다..")
    public ResponseEntity createLecture(@RequestBody CreateLectureRequestDTO createLectureRequest){
        User professor = userService.getUserInfoById(createLectureRequest.getProfessorId());
        Lecture lecture = createLectureRequest.toEntity(professor);
        return new ResponseEntity(BaseResponse.of(lectureService.createLecture(lecture), HttpStatus.CREATED), HttpStatus.CREATED);
    }

}
